package net.buj.rml.options;

import net.buj.rml.annotations.Nullable;

import java.util.Objects;

public abstract class GameOption<T> {
    public interface OnChangedListener<T> {
        void run(GameOption<T> newValue);
    }

    private final String[] category;
    private @Nullable String displayName;
    private final GameOptionUI<T> ui;
    private final String name;
    private T value;
    private @Nullable OnChangedListener<T> onChangedListener = null;

    public GameOption(String name, GameOptionUI<T> ui, String[] category, T value) {
        this.name = name;
        this.category = category;
        this.ui = ui;
        this.value = value;
    }

    public String[] getCategory() {
        return category;
    }

    public GameOptionUI<T> getUI() {
        return ui;
    }

    public @Nullable <U> GameOption<U> cast() {
        try {
            return (GameOption<U>) this;
        } catch (Throwable e) {
            System.err.println("Cast failed on option " + name);
            e.printStackTrace();
            return null;
        }
    }

    public String getDisplayName() {
        return Objects.toString(displayName, name);
    }

    public GameOption<T> withName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public GameOption<T> withListener(OnChangedListener<T> listener) {
        onChangedListener = listener;
        return this;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        if (onChangedListener != null)
            onChangedListener.run(this);
    }

    public abstract void from(String data);
    public abstract String into();

    public static class SliderIntegerGameOption extends GameOption<Integer> {
        public SliderIntegerGameOption(String name, String[] category, Integer value, Integer min, Integer max) {
            super(name, new GameOptionUI.Slider<Integer>() {

                @Override
                public void change(GameOption<Integer> option, float percent) {
                    option.setValue(Math.round(min + (float) (max - min) * percent));
                }

                @Override
                public float percent(GameOption<Integer> option) {
                    return (float) (option.getValue() - min) / (max - min);
                }

                @Override
                public String getName(GameOption<Integer> option) {
                    return option.getDisplayName() + ": " + option.getValue();
                }
            }, category, value);
        }

        @Override
        public void from(String data) {
            try {
                setValue(Integer.parseInt(data));
            } catch (Exception e) {
                System.err.println("Failed to parse property " + getName());
                e.printStackTrace();
            }
        }

        @Override
        public String into() {
            return getValue().toString();
        }
    }

    public static class IntegerGameOption extends GameOption<Integer> {
        public IntegerGameOption(String name, String[] category, Integer value) {
            super(name, new GameOptionUI.Text<Integer>() {
                @Override
                public String getInput(GameOption<Integer> option) {
                    return option.getValue().toString();
                }

                @Override
                public void input(GameOption<Integer> option, char ch) {
                    try {
                        option.setValue(Integer.parseInt(option.getValue().toString() + ch));
                    } catch (Exception ignored) {}
                }

                @Override
                public void backspace(GameOption<Integer> option) {
                    if (option.getValue().toString().length() == 1) {
                        option.setValue(0);
                    }
                    else try {
                        String value = option.getValue().toString();
                        option.setValue(Integer.parseInt(value.substring(0, value.length() - 1)));
                    } catch (Exception ignored) {}
                }

                @Override
                public String getName(GameOption<Integer> option) {
                    return option.getDisplayName();
                }
            }, category, value);
        }

        @Override
        public void from(String data) {
            try {
                setValue(Integer.parseInt(data));
            } catch (Exception e) {
                System.err.println("Failed to parse property " + getName());
                e.printStackTrace();
            }
        }

        @Override
        public String into() {
            return getValue().toString();
        }
    }

    public static class BooleanGameOption extends GameOption<Boolean> {
        public BooleanGameOption(String name, String[] category, Boolean value, String yesValue, String noValue) {
            super(name, new GameOptionUI.Button<Boolean>() {
                @Override
                public void execute(GameOption<Boolean> option) {
                    option.setValue(!option.getValue());
                }

                @Override
                public boolean getEnabled(GameOption<Boolean> option) {
                    return true;
                }

                @Override
                public String getName(GameOption<Boolean> option) {
                    return option.getDisplayName() + ": " + (option.getValue() ? yesValue : noValue);
                }
            }, category, value);
        }

        @Override
        public void from(String data) {
            setValue(data.equals("true"));
        }

        @Override
        public String into() {
            return getValue() ? "true" : "false";
        }
    }

    public static class StringGameOption extends GameOption<String> {
        public StringGameOption(String name, String[] category, String value) {
            super(name, new GameOptionUI.Text<String>() {
                @Override
                public String getInput(GameOption<String> option) {
                    return option.getValue();
                }

                @Override
                public void input(GameOption<String> option, char ch) {
                    option.setValue(option.getValue() + ch);
                }

                @Override
                public void backspace(GameOption<String> option) {
                    String value = option.getValue();
                    option.setValue(value.substring(0, value.length() - 1));
                }

                @Override
                public String getName(GameOption<String> option) {
                    return option.getDisplayName();
                }
            }, category, value);
        }

        @Override
        public void from(String data) {
            setValue(data);
        }

        @Override
        public String into() {
            return getValue();
        }
    }
}
