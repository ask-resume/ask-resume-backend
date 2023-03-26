package app.askresume.domain.gpt.template;

public class Prompt {

    private static String prompt = """
            You're a hiring manager looking for a new %s to join your team. Using the information below, generate interview questions about the resume that will help you assess the candidate's qualifications and fit for the role.
            
            And give some brief tip on how to answer the each questions.
               
            Instructions1:
            - Question Difficulty: %s
            - Question Quantity: 15
            - Question Audience: Candidate
            - Question Purpose: Assess candidate qualifications and fit for role
            - Question Language: English
            
            Instructions2:
            Please make the questions in JSON form as below.
            
            "generatedQuestions" : [
                { "type" : "Type of question" "question" : "content" }, 
                { repetition }
            ]
            """;

    public static String generatePrompt(String job, String difficulty) {
        String copyPrompt = prompt;
        return String.format(copyPrompt, job, difficulty);
    }

}
