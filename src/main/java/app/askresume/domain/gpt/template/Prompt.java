package app.askresume.domain.gpt.template;

public class Prompt {

    // And give some brief tip on how to answer the each questions.

    private static String prompt = """
            You're a hiring manager looking for a new %s to join your team. Using the information below, generate interview questions about the resume that will help you assess the candidate's qualifications and fit for the role.
            And please express the pros and cons of the applicant in one word. Three each.
            
            Instructions1:
            - Question Difficulty: %s
            - Question Quantity: 15
            - Question Audience: Candidate
            - Question Purpose: Assess candidate qualifications and fit for role
            - Question Language: English
            
            Instructions2:
            Please make the questions in JSON form as below.
            
            {
                "expectedQuestions" : [
                    { "type" : "Type of question", "question" : "content" }, 
                    { repetition }
                ],
                "strengths" : [
                    "strengths repetition",
                ],
                "disadvantages": [
                    "disadvantages repetition",
                ]
            }
            
            - You don't write ',' at the end.
            """;

    public static String generatePrompt(String job, String difficulty) {
        String copyPrompt = prompt;
        return String.format(copyPrompt, job, difficulty);
    }

}
