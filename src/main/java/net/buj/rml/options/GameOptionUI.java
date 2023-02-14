package net.buj.rml.options;

public interface GameOptionUI<T> {
    String getName(GameOption<T> option);

    interface Button<T> extends GameOptionUI<T> {
        void execute(GameOption<T> option);
        boolean getEnabled(GameOption<T> option);
    }

    interface Slider<T> extends GameOptionUI<T> {
        void change(GameOption<T> option, float percent);
        float percent(GameOption<T> option);
    }

    interface Text<T> extends GameOptionUI<T> {
        String getInput(GameOption<T> option);
        void input(GameOption<T> option, char ch);
        void backspace(GameOption<T> option);
    }

    class None<T> implements GameOptionUI<T> {
        private final String name;
        public None(String name) {
            this.name = name;
        }

        @Override
        public String getName(GameOption<T> option) {
            return name;
        }
    }
}
