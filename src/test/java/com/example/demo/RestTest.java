package com.example.demo;

import com.example.model.ProfileResultView;
import com.example.repository.ProfileResultDao;
import com.example.specification.SearchCriteria;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@DisplayName("When running RestTest")
public class RestTest {

    @Autowired
    private ProfileResultDao profileResultDao;

    private ProfileResultView profileRoman;
    private ProfileResultView profileAndrey;

    @BeforeAll
    void init() {
        profileRoman = new ProfileResultView(4, "Ресурс 1",
                "Роман", "Таблица 4",
                "Поле 1", "dworld.ru", "Я люблю этот мир!");

        profileAndrey = new ProfileResultView(1, "Ресурс 1",
                "Андрей", "Таблица 1", "Поле 1", "hello.world.ru",
                "Я люблю этот мир!");

    }

    @Nested
    @DisplayName("use search method")
    class SearchRest {

        @Test
        @DisplayName("given comment from ListProfileResultView and then correct")
         void givenComment_whenGettingListProfileResultView_thenCorrect() {
            List<SearchCriteria> params = new ArrayList<>();

            params.add(new SearchCriteria("comment", ":", "Я люблю этот мир!"));
            List<ProfileResultView> resultViews = profileResultDao.searchProfile(params);
            assertAll(() -> {
                assertEquals(profileRoman.getComment(), resultViews.get(0).getComment());
                assertEquals(profileAndrey.getComment(), resultViews.get(0).getComment());
            });
        }

        @Test
        @DisplayName("given profileID greater or less than 2 from ListProfileResultView and then correct")
         void givenProfileId_whenGettingListProfileResultView_thenCorrect() {
            List<SearchCriteria> params1 = new ArrayList<>();
            params1.add(new SearchCriteria("profileId", ">", "2"));
            List<SearchCriteria> params2 = new ArrayList<>();
            params2.add(new SearchCriteria("profileId", "<", "2"));
            List<ProfileResultView> resultViewGreater = profileResultDao.searchProfile(params1);
            List<ProfileResultView> resultViewLess = profileResultDao.searchProfile(params2);
            assertAll(() -> {
                assertEquals(profileRoman, resultViewGreater.get(0));
                assertEquals(profileAndrey, resultViewLess.get(0));
            });

        }

        @Test
        @DisplayName("given comment and fieldName from ListProfileResultView and then correct")
        void givenCommentAndFieldName_whenGettingListProfileResultView_thenCorrect() {
            List<SearchCriteria> params = new ArrayList<>();
            params.add(new SearchCriteria("fieldName", ":", "Поле 1"));
            params.add(new SearchCriteria("comment", ":", "Я люблю этот мир!"));
            List<ProfileResultView> resultViews = profileResultDao.searchProfile(params);
            assertAll(() -> {
                assertEquals(profileRoman, resultViews.get(0));
                assertEquals(profileAndrey, resultViews.get(1));
            });
        }

        @Test
        @DisplayName("given empty comment from ListProfileResultView and then correct")
         void givenEmptyComment_whenGettingListProfileResultView_thenCorrect() {
            List<SearchCriteria> params = new ArrayList<>();
            params.add(new SearchCriteria("comment", ":", "Такого комментария нет"));
            List<ProfileResultView> resultViews = profileResultDao.searchProfile(params);
            assertAll(() -> {
                assertNotEquals(profileRoman, resultViews.isEmpty());
                assertNotEquals(profileAndrey, resultViews.isEmpty());
            });
        }
    }
}
