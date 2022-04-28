package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private BoardService boardService;

    //게시글 목록
    // list 경로에 요청 파라미터가 있을 경우 (?page=1), 그에 따른 페이지네이션을 수행함.
    @GetMapping({"", "/list"})
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardList = boardService.getBoardList(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList",pageList);

        return "board/list";
    }

    //글쓰는 페이지
    @GetMapping("/post")
    public String write(){
        return "board/write";
    }
    
    //글을 쓴 뒤 POST메서드로 글 쓴 내용을 DB에 저장
    //그 후에는 /list 경로로 리디렉션
    @PostMapping("/post")
    public String write(BoardDto boardDto){
        boardService.savePost(boardDto);
        return "redirect:/board/list";
    }

    //게시물 상세 페이지, {no}로  페이지 넘버를 받는다.
    //PathVariable 애노테이션을 통해 no를 받음
    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model){
        BoardDto boardDto = boardService.getPost(no);

        model.addAttribute("boardDto", boardDto);
        return "board/detail";
    }
    
    //게시물 수정 페이지이며, {no}로 페이지 넘버를 받는다.
    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDto = boardService.getPost(no);

        model.addAttribute("boardDto", boardDto);
        return "board/update";
    }

    //위는 GET 메서드이며, PUT 메서드를 통해 게시물 수정한 부분에 대한 적용
    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDto){
        boardService.savePost(boardDto);

        return "redirect:/board/list";
    }

    //게시물 삭제는 deletePost 메서드를 사용하여 간단하게 삭제할 수 있다.
    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no){
        boardService.deletePost(no);

        return "redirect:/board/list";
    }

    //검색
    //keyword를 view로부터 전달받고
    //Service로부터 받은 boardDtoList를 model의 attribute로 전달해준다.
    @GetMapping("/board/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model){
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        model.addAttribute("boardList",boardDtoList);

        return "board/list";
    }
}
