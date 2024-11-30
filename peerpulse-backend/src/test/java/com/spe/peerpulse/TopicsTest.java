package com.spe.peerpulse;

import com.spe.peerpulse.entity.Topic;
import com.spe.peerpulse.repository.TopicRepository;
import com.spe.peerpulse.service.InstructorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopicsTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private InstructorService teacherService;

    private List<Topic> topics;

    @BeforeEach
    void setUp() {
        // create some test data
        topics = new ArrayList<>();
        topics.add(new Topic(1L, "Topic 1", "Description 1", null));
        topics.add(new Topic(2L, "Topic 2", "Description 2", null));
    }

    @Test
    void testFindAllTopics() {
        Long classId = 1L;

        // mock the repository method to return the test data
        when(topicRepository.findByClassObj_ClassId(classId)).thenReturn(topics);

        // call the service method
        List<Topic> result = teacherService.findAllTopics(classId);

        // assert that the result matches the test data
        assertEquals(topics, result);

        // verify that the repository method was called with the correct argument
        verify(topicRepository).findByClassObj_ClassId(classId);
    }

//    @Test
//    void testFindAllTopicsWithInvalidClassId() {
//        Long classId = 1L;
//
//        // mock the repository method to return an empty list
//        when(topicRepository.findByClassObj_ClassId(classId)).thenReturn(new ArrayList<>());
//
//        // call the service method and expect a ResourceNotFoundException to be thrown
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> teacherService.findAllTopics(classId));
//
//        // verify that the exception message is correct
//        String expectedMessage = "Failed to find all topics";
//        String actualMessage = exception.getMessage();
//        assertEquals(expectedMessage, actualMessage);
//
//        // verify that the repository method was called with the correct argument
//        verify(topicRepository).findByClassObj_ClassId(classId);
//
//        // log some output for debugging
//        System.out.println("testFindAllTopicsWithInvalidClassId: exception message = " + actualMessage);
//        System.out.println("testFindAllTopicsWithInvalidClassId: repository method called with argument = " + classId);
//    }


}
