package com.example.Baesh.Service;

import com.example.Baesh.DTO.OllamaResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LocalLMMService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String OLLAMA_URL = "http://localhost:11434/api/generate";

    //유저 태그화또는 태그로 검색 함수
    public List<String> extractTag(String userIntro){
        String prompt = userTagBuildprompt(userIntro);
        return parseJsonArrayFrom(localLLMRequset(prompt));
    }

    public String googleProgrammableSearch(String userIntro){
        String prompt = googleSearchBuildprompt(userIntro);
        return localLLMRequset(prompt);
    }

    private String localLLMRequset(String userPrompt){
        Map<String,Object> request = new HashMap<>();
        request.put("model","gemma3:12b"); //모델명(ID 말고 NAME)
        request.put("prompt", userPrompt);
        request.put("stream", false); //응답 한번에 받음

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<OllamaResponse> response = restTemplate.exchange(
                OLLAMA_URL,
                HttpMethod.POST,
                entity,
                OllamaResponse.class
        );

        return Objects.requireNonNull(response.getBody(), "Ollama 응답이 null입니다").getResponse();
    }

    //로컬 LLM에 전달할 유저 태그 프로프트 생성 함수
    private String userTagBuildprompt(String intro){
        return """
                다음 자기소개에서 직무, 기술, 분야, 경력을 태그로 뽑아줘.
                형식: "["Java", "Spring", "백엔드","1년차"]"
                자기소개:
                """+intro;
    }

    //로컬 LLM에 전달할 유저 외부 검색 프로프트 생성 함수
    private String googleSearchBuildprompt(String intro){
        return"""
            다음 문장을 웹 검색 키워드로 바꿔줘.
            조건:
            - 구인/채용/구직/리쿠르팅 사이트는 제외하고,
            - 개인 블로그, GitHub, 기술 포트폴리오 중심의 키워드로 구성해줘.
            - 결과는 쌍따옴표 없이 공백 기준 단어 10개 이내의 구글 검색 쿼리로 만들어줘.
            문장: """+intro;
    }

    //LMM 답변 리스트로 파싱
    private List<String> parseJsonArrayFrom(String text){
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(text, new TypeReference<List<String>>(){});
        } catch (Exception e){
            throw new RuntimeException("LMM 응답 파싱 실패: "+text);
        }
    }
}
