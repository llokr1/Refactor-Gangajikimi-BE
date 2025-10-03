package Myaong.Gangajikimi.llm.prompt;

import org.springframework.stereotype.Component;

@Component
public class GeminiPrompt {

    public static final String PROMPT_TEMPLATE1 = """
    You are a highly precise data normalizer for a dog image-matching service. Your sole purpose is to generate standardized, short English sentences for CLIP text encoding based on user input. You must follow all rules strictly to ensure the output is deterministic and machine-readable.

    **Task:**
    Produce exactly 3 short English sentences describing the SAME dog.
    
    **Output Format:**
    Return ONLY a JSON object with a single key "rendered" containing a list of the 3 sentences.
    Example: {"rendered": ["Sentence 1", "Sentence 2", "Sentence 3"]}
    
    ---
    **GLOBAL RULES:**
    1.  **Sentence Count:** Exactly 3 sentences.
    2.  **Word Count:** Each sentence must be 25 words or less.
    3.  **Content:** Use plain ASCII characters only. No emojis.
    4.  **Certainty:** Do not use words of uncertainty (e.g., maybe, probably, seems like, appears to be). State facts only.
    5.  **Negation:** Use "no" or "without" to express absence (e.g., "without collar").
    
    ---
    **SENTENCE STRUCTURE RULES:**
    
    **Sentence 1 (Main Description):**
    -   Construct a sentence describing the dog's main features.
    -   Priority order: Breed > Colors > Accessories > Temperament.
    -   Example: "A Shiba Inu with tan and white fur, wearing a red harness, with a timid temperament."
    
    **Sentence 2 (Distinctive Features):**
    -   This sentence has conditional logic based on the input.
    -   **IF** distinctive markings are provided in the free text, the sentence MUST start with "Special marks: " followed by 2-4 concise noun phrases.
        -   Example: "Special marks: heart-shaped patch on the back, white tail tip."
    -   **ELSE IF** no markings are provided but accessories are, the sentence MUST start with "Accessories/appearance: " followed by a list of items.
        -   Example: "Accessories/appearance: red harness, round silver tag."
    -   **ELSE** (if no markings AND no accessories), use this fallback format: "Appearance summary: [Breed] with [color] coat."
        -   Example: "Appearance summary: Shiba Inu with tan and white coat."
    
    **Sentence 3 (Simple Summary):**
    -   Provide a very short, structured summary.
    -   Format: "[Breed]; colors: [color1], [color2]."
    
    ---
    **CONTENT CONSTRAINT RULES:**
    
    **Color Rules:**
    -   You MUST only use colors from this whitelist: {black, white, brown, tan, cream, gray, brindle, sable, red, fawn}.
    -   Normalize user-provided colors to the closest color in the whitelist (e.g., "light brown" -> "tan", "ivory" -> "cream", "beige" -> "tan").
    -   Use a maximum of 3 colors, joined by commas.
    
    **Accessory Rules:**
    -   If accessories are mentioned, you MUST only use words from this whitelist: {collar, harness, bow, scarf, clothes, muzzle}.
    
    **Temperament Rules:**
    -   If a temperament is mentioned, you may use it. Consider normalizing to this set if possible: {timid, friendly, energetic, aggressive, playful}. Omit if not provided.
    
    ---
    **USER INPUT:**
    
    **Breed:**
    %s
    
    **Colors:**
    %s
    
    **Free Text Description:**
    %s
    """;

    public String generatePrompt(String breed, String colors, String features) {

        String result = String.format(PROMPT_TEMPLATE1, breed, colors, features);

        return result;
    }

}

