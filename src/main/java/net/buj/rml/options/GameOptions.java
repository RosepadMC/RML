package net.buj.rml.options;

import net.buj.rml.annotations.Nullable;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;

public abstract class GameOptions {
    private KeyBinding[] keybindings = new KeyBinding[0];
    private GameOption<?>[] options = new GameOption<?>[0];
    private final File file;

    public int keyCodeFor(String name) {
        for (KeyBinding binding : keybindings)
            if (binding.name.equals(name)) return binding.code;
        return -1;
    }

    public @Nullable KeyBinding bindingFor(String name) {
        for (KeyBinding binding : keybindings)
            if (binding.name.equals(name)) return binding;
        return null;
    }

    public List<KeyBinding> bindings() {
        return Arrays.asList(keybindings);
    }

    public KeyBinding setKeyBinding(String name, String description, int code) {
        for (KeyBinding binding : keybindings)
            if (binding.name.equals(name)) throw new RuntimeException("Key is already present");
        KeyBinding bind = new KeyBinding(name, description, code);
        KeyBinding[] newList = new KeyBinding[keybindings.length + 1];
        System.arraycopy(keybindings, 0, newList, 0, keybindings.length);
        newList[keybindings.length] = bind;
        keybindings = newList;
        return bind;
    }

    public void setKeyBinding(String name, int code) {
        @Nullable KeyBinding bind = bindingFor(name);
        if (bind == null) throw new RuntimeException("Key is not defined");
        bind.code = code;
    }

    private String delimiter = "=";

    public Collection<GameOption<?>> list() {
        return Arrays.asList(options);
    }

    public <T> void setOption(GameOption<T> option) {
        for (int i = 0; i < options.length; i++)
            if (options[i].getName().equals(option.getName())) {
                options[i] = option;
                return;
            }

        GameOption<?>[] newList = new GameOption<?>[options.length + 1];
        System.arraycopy(options, 0, newList, 0, options.length);
        newList[options.length] = option;
        options = newList;
    }

    public GameOptions(File propertiesFile) {
        file = propertiesFile;
    }

    public <T> @Nullable GameOption<T> getOption(String name) {
        for (GameOption<?> option : options)
            if (option.getName().equals(name)) {
                try {
                    return option.<T>cast();
                } catch (Exception ignored) {
                    return null;
                }
            }
        return null;
    }
    public void save() {
        try {
            PrintWriter writer = new PrintWriter(Files.newOutputStream(file.toPath()));
            for (GameOption<?> option : options) {
                writer.println(option.getName() + delimiter + option.into());
            }
            for (KeyBinding binding : keybindings) {
                writer.println("&" + binding.name + delimiter + binding.code);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void load() {
        if (file.exists()) {
            try {
                Scanner scanner = new Scanner(Files.newInputStream(file.toPath()));
                while (scanner.hasNextLine()) {
                    final String line = scanner.nextLine().trim();
                    if (line.trim().startsWith("#")) {
                        continue;
                    }
                    int index = Math.max(line.indexOf(":"), line.indexOf("="));
                    if (index != -1) {
                        String name = line.substring(0, index);
                        String value = line.substring(index + 1);
                        if (line.trim().startsWith("&")) {
                            name = name.substring(1);
                            try {
                                setKeyBinding(name, Integer.parseInt(value));
                            } catch (Exception err) {
                                System.err.append("Failed to load key " + name + ":");
                                err.printStackTrace();
                            }
                        }
                        else try {
                            GameOption<?> option;
                            if ((option = getOption(name)) != null) {
                                option.from(value);
                            }
                        } catch (Exception e) {
                            System.err.println("Failed to load property " + name + ":");
                            e.printStackTrace();
                        }
                        continue;
                    }
                    try {
                        GameOption<?> option;
                        if ((option = getOption(line)) != null) {
                            option.<Boolean>cast().setValue(true);
                        }
                    } catch (Exception e) {
                        System.err.println("Failed to set property " + line + ":");
                        e.printStackTrace();
                    }
                }
                scanner.close();
            } catch (Exception e) {
                e.printStackTrace();
                save();
            }
        }
        else {
            save();
        }
    }

    protected void setDelimiter(String d) {
        delimiter = d;
    }
}
