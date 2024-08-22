"""
This is the emotion_detector function which uses the Watson NLP library.
"""

# Import the requests library to handle HTTP requests
import requests
# Needed to make the string a dictionary
import json

# Define a function named emotion detector that takes a string input (text_to_analyse)
def emotion_detector(text_to_analyze):
    """
    Makes the dominant emotion the emotion with the highest score based on user text
    """
    if not text_to_analyze:
        return {
            'anger': None,
            'disgust': None,
            'fear': None,
            'joy': None,
            'sadness': None,
            'dominant_emotion': None
        }

    url = 'https://sn-watson-emotion.labs.skills.network/v1/watson.runtime.nlp.v1/NlpService/EmotionPredict'
    header = {"grpc-metadata-mm-model-id": "emotion_aggregated-workflow_lang_en_stock"}
    myobj = { "raw_document": { "text": text_to_analyze } }
    response = requests.post(url, json = myobj, headers=header)

    if response.status_code == 400:
        return {
            'anger': None,
            'disgust': None,
            'fear': None,
            'joy': None,
            'sadness': None,
            'dominant_emotion': None
        }

    # Parsing the JSON response from the API
    response_info = json.loads(response.text)
    # Get all the emotions and their scores
    # Default is 0 in case the emotion is not found
    emotions = response_info.get('emotionPredictions')[0].get('emotion')
    anger_score = emotions.get('anger', 0)
    disgust_score = emotions.get('disgust', 0)
    fear_score = emotions.get('fear', 0)
    joy_score = emotions.get('joy', 0)
    sadness_score = emotions.get('sadness', 0)
    # Get the emotion with the highest score
    dominant_emotion = max(emotions, key = emotions.get, default = None)
    # Format the responses
    formatted_response = {
                        'anger': anger_score,
                        'disgust': disgust_score,
                        'fear': fear_score,
                        'joy': joy_score,
                        'sadness': sadness_score,
                        'dominant_emotion': dominant_emotion
    }
    # Returning a dictionary containing emotion results
    return formatted_response
