import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Letter {
    private char character;

    public Letter(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }

    public boolean isConsonant() {
        char lowerChar = Character.toLowerCase(character);
        return "bcdfghjklmnpqrstvwxyz".indexOf(lowerChar) >= 0;
    }
}

class Word {
    private List<Letter> letters;

    public Word(String word) {
        letters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            letters.add(new Letter(c));
        }
    }

    public String getWord() {
        StringBuilder wordBuilder = new StringBuilder();
        for (Letter letter : letters) {
            wordBuilder.append(letter.getCharacter());
        }
        return wordBuilder.toString();
    }

    public boolean shouldRemove(int length) {
        if (letters.size() != length) {
            return false;
        }
        return letters.get(0).isConsonant();
    }
}

class Sentence {
    private List<Object> components;

    public Sentence(String sentence) {
        components = new ArrayList<>();
        String[] parts = sentence.split("(?=[,.!?])|\s+");
        for (String part : parts) {
            if (part.matches("[,.!?]")) {
                components.add(part);
            } else {
                components.add(new Word(part));
            }
        }
    }

    public String getSentence() {
        StringBuilder sentenceBuilder = new StringBuilder();
        for (Object component : components) {
            if (component instanceof Word) {
                sentenceBuilder.append(((Word) component).getWord()).append(" ");
            } else {
                sentenceBuilder.append(component.toString());
            }
        }
        return sentenceBuilder.toString().trim();
    }

    public void filterWords(int length) {
        components.removeIf(component -> component instanceof Word && ((Word) component).shouldRemove(length));
    }
}


class Text {
    private List<Sentence> sentences;

    public Text(String text) {
        sentences = new ArrayList<>();
        text = text.replaceAll("[\t\n\r]+", " ").replaceAll("\s+", " ").trim();
        String[] sentenceParts = text.split("(?<=[.!?])\s+");
        for (String sentence : sentenceParts) {
            sentences.add(new Sentence(sentence));
        }
    }

    public String getText() {
        StringBuilder textBuilder = new StringBuilder();
        for (Sentence sentence : sentences) {
            textBuilder.append(sentence.getSentence()).append(" ");
        }
        return textBuilder.toString().trim();
    }

    public void filterWords(int length) {
        for (Sentence sentence : sentences) {
            sentence.filterWords(length);
        }
    }
}

public class RemoveWords {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

    
        System.out.println("Введіть текст:");
        String inputText = scanner.nextLine();

      
        System.out.println("Введіть довжину слова для видалення:");
        int wordLength = scanner.nextInt();
        scanner.nextLine(); 

        Text text = new Text(inputText);
        text.filterWords(wordLength);

        System.out.println("Результат:");
        System.out.println(text.getText());
    }
}
