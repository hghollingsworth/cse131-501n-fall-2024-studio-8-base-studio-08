package studio8;

public class SelectAllQuestion extends MultipleChoiceQuestion {
	private String[] choices; 

    public SelectAllQuestion(String prompt, String answer, String[] choices) {
        // Call superclass constructor and set 1 point per choice
        super(prompt, answer, choices.length, choices);
        this.choices = choices; 
    }

    @Override
    public int checkAnswer(String givenAnswer) {
        int points = 0;

        // Check for correct and incorrect answers
        int missingCorrectAnswers = findMissingCorrectAnswers(givenAnswer);
        int incorrectGivenAnswers = findIncorrectGivenAnswers(givenAnswer);

        // Calculate points by subtracting penalties from total possible points
        points = getPoints() - missingCorrectAnswers - incorrectGivenAnswers;

        // Ensure points do not go negative
        return Math.max(points, 0);
    }

    // Find how many correct answers are missing from the given answer
    private int findMissingCorrectAnswers(String givenAnswer) {
        String answer = this.getAnswer();
        return findMissingCharacters(givenAnswer, answer);
    }

    // Find how many incorrect answers are present in the given answer
    private int findIncorrectGivenAnswers(String givenAnswer) {
        String answer = this.getAnswer();
        return findMissingCharacters(answer, givenAnswer);
    }

    /*
     * Returns the number of characters in toCheck that are missing from the
     * baseString. For example findMissingCharacters("hi", "hoi") would return 1,
     * since 'o' is not in the baseString.
     */
    private static int findMissingCharacters(String baseString, String toCheck) {
        int missingValues = 0;
        for (int i = 0; i < toCheck.length(); i++) {
            char characterToLocate = toCheck.charAt(i);
            if (baseString.indexOf(characterToLocate) == -1) { // Character not in baseString
                missingValues++;
            }
        }
        return missingValues;
    }	
	
	public static void main(String[] args) {
		String[] choices = {"instance variables", "git", "methods", "eclipse"};
		Question selectAll = new SelectAllQuestion("Select all of the following that can be found within a class:", "13", choices);
		selectAll.displayPrompt();
		System.out.println(selectAll.checkAnswer("hi")); //no credit
		System.out.println(selectAll.checkAnswer("2")); //1 point
		System.out.println(selectAll.checkAnswer("13")); //full credit
		System.out.println(selectAll.checkAnswer("31")); //full credit
		System.out.println(selectAll.checkAnswer("1")); //3 points
		System.out.println(selectAll.checkAnswer("3")); //3 points
		System.out.println(selectAll.checkAnswer("23")); //2 points
		System.out.println(selectAll.checkAnswer("34")); //2 points
		System.out.println(selectAll.checkAnswer("4")); //1 point
		System.out.println(selectAll.checkAnswer("124")); //1 point
		System.out.println(selectAll.checkAnswer("24")); //0 points
		
	}
}
