INSERT INTO JOB_MASTER (id, master_name, created_at)
VALUES (1, 'backend developer', now()),
       (2, 'frontend developer', now());

INSERT INTO JOB (locale, name, job_master_id, created_at)
VALUES ('KO', '백엔드 개발자', 1, now()),
       ('EN', 'backend developer', 1, now()),
       ('KO', '프론트 개발자', 2, now()),
       ('EN', 'front developer', 2, now());

INSERT INTO PROMPT (prompt_type, description, content)
VALUES ( 'INTERVIEW_MAKER'
       , '이력서 내용 기반, 예상 질문, 모범 답안 생성 프롬프트'
       , 'You''re a hiring manager looking for a new ''%s'' to join your team.
          Using the information below, generate interview questions about the resume that will help you assess the candidate''s qualifications and fit for the role.
          In addition, the model answer to the created question must also be created.

          Do not use '', "",{}, (), [] or emojis in all content.

          Below is the information about ''%s'' of the applicant.

          Instructions1:
          - Question Difficulty: ''%s''
          - Question Quantity: 3
          - Question Purpose: Assess candidate qualifications and fit for role
          - Applicant Levels: ''%s''
          - Question Language: ''%s''

          Instructions2:
          You must write your question unconditionally and absolutely in JSON format, as shown below.
          Never add anything else. numbers, letters, etc.

          {
              "interviews" : [
                  { "question" : "content", "bestAnswer" : "content" },
                  { repetition }
              ]
          }

          - You don''t write '','' at the end.')