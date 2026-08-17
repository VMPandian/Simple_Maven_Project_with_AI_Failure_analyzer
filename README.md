Sure. Create/replace **`README.md`** with this single file content:

````markdown
# AI Failure Analyzer - POC

A simple Java Proof of Concept that uses the Groq API to analyze software test failure messages and provide:

- Failure Category
- Top 3 Possible Root Causes
- Top 3 Solutions

The purpose of this POC is to validate whether an AI model can provide useful and actionable analysis of automation test failures.

---

## Tech Stack

- Java 26
- Maven
- Groq API
- GPT-OSS-20B
- Jackson
- dotenv-java

---

## Project Structure

```text
Simple_Maven_Project_with_AI_Failure_analyzer/
│
├── .env
├── .gitignore
├── README.md
├── skill.md
├── pom.xml
│
└── src/
    └── main/
        └── java/
            └── com/
                └── qa/
                    └── ai/
                        ├── Main.java
                        ├── GroqClient.java
                        ├── FailureAnalyzer.java
                        └── FailureAnalysis.java
```

---

## How It Works

```text
Failure Message
      ↓
FailureAnalyzer
      ↓
GroqClient
      ↓
Groq API
      ↓
AI Analysis
      ↓
JSON Response
      ↓
FailureAnalysis Java Object
      ↓
Display Results
```

---

## AI Analysis

The AI instructions are defined in the `skill.md` file.

For every failure, the AI provides:

### Failure Category

Example:

```text
Locator Failure
```

### Top 3 Possible Root Causes

```text
1. Incorrect XPath
2. Element not loaded when Selenium searched for it
3. Element is inside an iframe
```

### Top 3 Solutions

```text
1. Verify the XPath against the current DOM
2. Add an explicit wait
3. Check whether the element is inside an iframe
```

---

## Example Input

```text
org.openqa.selenium.NoSuchElementException:
Unable to locate element:
{"method":"xpath","selector":"//button[@id='login']"}
```

---

## Example Output

```text
======================================
Failure Category: Locator Failure

Top 3 Possible Root Causes:

1. The XPath selector does not match any element.
2. The element is not yet present in the DOM.
3. The element may reside inside an iframe.

Top 3 Solutions:

1. Verify the XPath against the current DOM.
2. Add an explicit wait before locating the element.
3. Check whether the element is inside an iframe.
```

---

## Environment Configuration

The POC uses a `.env` file to store Groq configuration.

Create `.env` in the project root:

```env
GROQ_API_KEY=your_groq_api_key
GROQ_API_URL=https://api.groq.com/openai/v1/chat/completions
GROQ_MODEL=openai/gpt-oss-20b
```

### Configuration

| Variable | Description |
|---|---|
| `GROQ_API_KEY` | Groq API authentication key |
| `GROQ_API_URL` | Groq API chat completion endpoint |
| `GROQ_MODEL` | AI model used for failure analysis |

**Do not commit `.env` to Git.**

The `.gitignore` should contain:

```gitignore
.env
target/
```

---

## AI Instructions

The `skill.md` file defines how the AI should analyze failures.

The model is instructed to:

- Categorize the failure
- Provide exactly 3 possible root causes
- Provide exactly 3 solutions
- Return valid JSON
- Keep the analysis concise and actionable
- Avoid claiming uncertain causes as confirmed

Expected JSON structure:

```json
{
  "failureCategory": "Locator Failure",
  "possibleRootCauses": [
    "Incorrect locator",
    "Element not loaded",
    "Incorrect browser context"
  ],
  "solutions": [
    "Verify the locator",
    "Add an explicit wait",
    "Check browser context"
  ]
}
```

---

## Build

From the project root, run:

```powershell
mvn clean compile
```

Expected result:

```text
BUILD SUCCESS
```

---

## Run

Run the POC using:

```powershell
mvn exec:java
```

The application will send the sample failure message to Groq and display the AI-generated analysis.

---

## Purpose of This POC

This POC validates the basic concept:

```text
Automation Failure
        ↓
       AI
        ↓
Failure Classification
        ↓
Root Cause Analysis
        ↓
Recommended Solutions
```

The current implementation is a standalone proof of concept and is not yet integrated with a Selenium/TestNG automation framework.
````
