package app.askresume.external.gpt.template;

public class Prompt {
    public static String generatePrompt(String job, String difficulty, String careerYear, String resumeType, String locale) {
        String prompt = """
                You're a hiring manager looking for a new '%s' to join your team.
                Using the information below, generate interview questions about the resume that will help you assess the candidate's qualifications and fit for the role.
                In addition, the model answer to the created question must also be created.
                            
                Do not use '', "",{}, (), [] or emojis in all content.
                            
                Below is the information about '%s' of the applicant.
                            
                Instructions1:
                - Question Difficulty: '%s'
                - Question Quantity: 3
                - Question Purpose: Assess candidate qualifications and fit for role
                - Applicant Levels: '%s'
                - Question Language: '%s'
                            
                Instructions2:
                You must write your question unconditionally and absolutely in JSON format, as shown below.
                Never add anything else. numbers, letters, etc.
                            
                {
                    "predictionResponse" : [
                        { "question" : "content", "bestAnswer" : "content" },
                        { repetition }
                    ]
                }
                            
                - You don't write ',' at the end.
                """;
        return String.format(prompt, job, resumeType, difficulty, careerYear, locale);
    }
}
