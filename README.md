# Ask Resume - Interview questions and answers generator for your resume

[한국어 Readme](https://github.com/dev-redo/ask-resume-front/blob/main/Korean.md)

This service is an LLM service to generate interview questions and answers for your resume.

We used the GPT-3.5-Turbo model for this service. GPT-3.5-Turbo is OpenAI's language model which powers the popular ChatGPT.

Enter your resume information and check out the Q&A that will come up in the interview!

> Frontend repository: https://github.com/dev-redo/ask-resume-front

> Backend repository: https://github.com/132262B/ask-resume-backend

<br />

# How to use?

1. Clicking the button on the landing page will take you to the form page where you can enter your resume.

2. On the form page, enter your personal information (desired occupation, experience, etc.) and your resume. <br />
   (Note: If you change the language, it will be refreshed and the values ​​you entered may be lost!)

3. Submitting after inputting will generate questions and possible answers from your resume. The generated results can be saved as a txt file.

<br />

## example of use

# Caption

## 1. Why is it failing to produce results?

If you enter your resume and generate results, a server error (HTTP status 500) may occur.

Sorry bro, This error occurs because the GPT server blocks requests when a large number of requests come in. <br />
Therefore, it has been implemented so that a re-request can be made when the issue occurs.

We will try to resolve the issue as soon as possible. Really sorry for the inconvenience.

<br />

# Credit

This service is inspired by [DevPort](https://github.com/custardcream98/DevPort) and [gpt4-pdf-chatbot-langchain](https://github.com/mayooear/gpt4-pdf-chatbot-langchain)

<br />

# Contact

If you find an error while using it or have a desired function, Please contact me by [Discord](https://discord.gg/aTzGNZ3y) or write Issue!
