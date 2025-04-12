package com.audiotranscriber.Audio_Transcriber;

import org.springframework.ai.audio.transcription.AudioTranscription;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/audio")
public class Controller {

    private final OpenAiAudioTranscriptionModel transcriptionModel;
    private Resource audioFile;
    private AudioTranscriptionPrompt transcriptionRequest;

    public Controller(@Value("${spring.ai.openai.api-key}") String apiKey) {
        OpenAiAudioApi openAiAudioApi = new OpenAiAudioApi(apiKey);
        this.transcriptionModel = new OpenAiAudioTranscriptionModel(openAiAudioApi);
    }
    @PostMapping("/transcribe")
    public ResponseEntity<AudioTranscription> transaudio(
            @RequestParam("file")MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("audio", "wav");
        file.transferTo(tempFile);


        FileSystemResource audioFile = new FileSystemResource("/path/to/your/resource/speech/jfk.flac");

        OpenAiAudioTranscriptionOptions transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .language("en")
                .temperature(0f)
                .build();

        AudioTranscriptionPrompt transcriptionPrompt = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
        AudioTranscriptionResponse response = transcriptionModel.call(transcriptionPrompt);

    

    tempFile.delete();
    return new ResponseEntity<>(response.getResult(), HttpStatus.OK);
    }
}
