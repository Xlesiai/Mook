import whisper

def speechToText(audio):
    save = audio.split('.')[0] + ".txt"
    model = whisper.load_model("base")  # Load a Whisper model (you can choose different sizes)
    result = model.transcribe(audio)
    with open(save, "w") as f:
        f.write(result["text"])


