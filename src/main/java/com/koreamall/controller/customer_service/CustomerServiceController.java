package com.koreamall.controller.customer_service;


import com.koreamall.dto.FileDTO;
import com.koreamall.dto.customer_service.OneOnOneAnswerDTO;
import com.koreamall.dto.customer_service.OneOnOneDTO;
import com.koreamall.service.customer_service.CustomerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/customer_service")
public class CustomerServiceController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private HttpSession httpSession;

    // redirect용
    private String create_redirect_view_url(String path) {
        return "redirect:/customer_service/" + path;
    }

    // view용
    private String create_view_url(String path) {
        return "customer_service/" + path;
    }

    @GetMapping("/faq")
    public String get_faq() {
        return create_view_url("faq");
    }

    @GetMapping("/one_on_one")
    public String get_one_on_one(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String keyword,
            Model model
    ) {
        System.out.println("get - get_one_on_one");
        List<OneOnOneDTO> oneOnOneDTOS = customerService.get_posts(query, keyword);
        model.addAttribute("oneOnOnes", oneOnOneDTOS);
        return create_view_url("one-on-one");
    }

    @GetMapping("/view/{postNo}")
    public String get_view(
            @PathVariable("postNo") Integer postNo,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            HttpServletResponse response,
            @CookieValue(value = "one-on-one", required = false) Cookie oneOnOneCookie,
            Model model
    ) {
        boolean cookieResult = is_entered(response, oneOnOneCookie, postNo);
        Object isPasswordCorrect = session.getAttribute("isPasswordCorrect");
        // 여기는 목록에서 클릭으로 들어온 것이다.
        if (Objects.nonNull(isPasswordCorrect)) {
            Boolean isCorrect = (Boolean) isPasswordCorrect;
            session.removeAttribute("isPasswordCorrect");
            // 비밀번호가 일치하지 않는다면
            if (!isCorrect) {
                redirectAttributes.addFlashAttribute("isError", true);
                return "redirect:/customer_service/one_on_one";
            }
            // 비밀번호가 일치한다면
            else {
                if (!cookieResult) {
                    customerService.increase_post_view_count(postNo);
                }
                OneOnOneDTO oneOnOne = customerService.get_post(postNo);
                model.addAttribute("oneOnOne", oneOnOne);
                return create_view_url("view");
            }
        }
        session.removeAttribute("isPasswordCorrect");
        // 여기는 클릭으로 온 게 아니라 url로 온 것이다.
        // 해당 게시물에 패스워드가 걸려있는지 확인하는 절차를 거친다
        // 이 게시물이 비밀번호가 존재하는 게시물이라면
        if (!customerService.get_post_isOpened(postNo)) {
            redirectAttributes.addFlashAttribute("isError", true);
            return "redirect:/customer_service/one_on_one";
        }
        // 비밀번호가 존재하지 않는다면 들어간다
        if (!cookieResult) {
            customerService.increase_post_view_count(postNo);
        }
        OneOnOneDTO oneOnOne = customerService.get_post(postNo);
        model.addAttribute("oneOnOne", oneOnOne);
        return create_view_url("view");
    }

    /***
     * 유저가 해당 게시물에 들어간 적이 있는지 없는지 반환
     @param response: HttpServletResponse 객체 - 쿠키 응답을 위함
     @return true: 게시물이 이미 조회된 적이 있음 false: 게시물이 조회된적이 없음
     ***/

    private boolean is_entered(
            HttpServletResponse response,
            Cookie oneOnOneCookie,
            Integer postNo
    ) {
        // 쿠키가 존재하지 않는다면 새로 쿠키를 생성
        if (Objects.isNull(oneOnOneCookie)) {
            Cookie newOneOnOneCookieCookie = new Cookie("one-on-one", String.valueOf(postNo));
            newOneOnOneCookieCookie.setMaxAge(60); // 최대 1분
            response.addCookie(newOneOnOneCookieCookie); // 클라이언트에 전송
            return false;
        }
        // 쿠키가 존재한다면 기존 쿠키에 값을 받아옴
        else {
            String value = URLDecoder.decode(oneOnOneCookie.getValue(), StandardCharsets.UTF_8);
            System.out.println(value);
            // 현재 게시물이 조회된 적이 있는지 확인
            if (!value.contains(String.valueOf(postNo))) {
                // 조회된 적이 없다면 값을 추가하고 업데이트
                value = URLEncoder.encode(value + "," + postNo, StandardCharsets.UTF_8);
                oneOnOneCookie.setMaxAge(60);
                oneOnOneCookie.setValue(value);
                response.addCookie(oneOnOneCookie);
                return false;
            }
        }
        return true;
    }


    @GetMapping("/write")
    public void get_write() {
    }

    @PostMapping("/write")
    public String post_write(OneOnOneDTO oneOnOneDTO) {
        System.out.println("post - write: " + oneOnOneDTO);
        customerService.create_post(oneOnOneDTO);
        return create_redirect_view_url("one_on_one");
    }

    /****************************************************************/
    @PostMapping("/one_on_one/answer")
    public String post_one_on_one_answer(
            OneOnOneAnswerDTO oneOnOneAnswerDTO

    ) {
        System.out.println("post - one_on_one_answer: " + oneOnOneAnswerDTO);
        // one_on_one_no 번호의 게시물에 대해 답변을 작성한다
        customerService.create_answer(oneOnOneAnswerDTO);
        // 현재 게시물로 재 GET 요청을 한다
        return "redirect:/customer_service/view/" + oneOnOneAnswerDTO.getOneOnOneNo();
    }


    /****************************************************************/

    @GetMapping("/one_on_one/file/{UUID}")
    public ResponseEntity<byte[]> get_one_on_one_file(
            @PathVariable("UUID") String UUID
    ) {
        System.out.println("get_one_on_one_file - file download 시도...");
        FileDTO fileDTO = customerService.get_file_of_one_on_one(UUID);
        if (Objects.isNull(fileDTO)) {
            System.out.println("ERROR: file not founded!");
            return ResponseEntity.notFound().build();
        }
        System.out.println("file founded!: " + fileDTO);
        byte[] data = fileDTO.getData();
        String originalFileNameEncoded = URLEncoder.encode(fileDTO.getOriginalFileName(), StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream");
        headers.add("Content-Disposition", "attachment; filename=" + originalFileNameEncoded);
        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }

    @GetMapping("/one_on_one/answer/file/{UUID}")
    public ResponseEntity<byte[]> get_one_on_one_answer_file(@PathVariable("UUID") String UUID) {
        FileDTO fileDTO = customerService.get_file_of_one_on_one_answer(UUID);
        if (Objects.isNull(fileDTO)) {
            return ResponseEntity.notFound().build();
        }

        byte[] data = fileDTO.getData();
        String originalFileNameEncoded = URLEncoder.encode(fileDTO.getOriginalFileName(), StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream");
        headers.add("Content-Disposition", "attachment; filename=" + originalFileNameEncoded);

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }
}
