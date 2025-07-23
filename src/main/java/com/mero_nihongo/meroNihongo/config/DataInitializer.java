package com.mero_nihongo.meroNihongo.config;

import com.mero_nihongo.meroNihongo.model.*;
import com.mero_nihongo.meroNihongo.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CourseRepository courseRepository;

    public DataInitializer(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (courseRepository.count() == 0) {
            initializeSampleCourses();
        }
    }

    private void initializeSampleCourses() {
        // Beginner Courses
        Course hiraganaBasics = new Course(
                "Hiragana Basics",
                "Learn the fundamental Japanese hiragana characters. Master reading and writing all 46 basic hiragana characters.",
                CourseLevel.BEGINNER,
                CourseCategory.HIRAGANA
        );
        hiraganaBasics.setTotalLessons(20);
        hiraganaBasics.setEstimatedHours(10);
        hiraganaBasics.setImageUrl("https://images.unsplash.com/photo-1528164344705-47542687000d?w=400");

        Course katakanaBasics = new Course(
                "Katakana Basics",
                "Master the katakana writing system used for foreign words in Japanese.",
                CourseLevel.BEGINNER,
                CourseCategory.KATAKANA
        );
        katakanaBasics.setTotalLessons(18);
        katakanaBasics.setEstimatedHours(9);
        katakanaBasics.setImageUrl("https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400");

        Course basicGrammar = new Course(
                "Japanese Grammar Fundamentals",
                "Learn essential Japanese grammar patterns and sentence structures for beginners.",
                CourseLevel.BEGINNER,
                CourseCategory.GRAMMAR
        );
        basicGrammar.setTotalLessons(25);
        basicGrammar.setEstimatedHours(15);
        basicGrammar.setImageUrl("https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=400");

        // Intermediate Courses
        Course kanjiIntro = new Course(
                "Introduction to Kanji",
                "Start your kanji journey with the most common 100 kanji characters.",
                CourseLevel.INTERMEDIATE,
                CourseCategory.KANJI
        );
        kanjiIntro.setTotalLessons(30);
        kanjiIntro.setEstimatedHours(20);
        kanjiIntro.setImageUrl("https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=400");

        Course conversationPractice = new Course(
                "Daily Conversation Practice",
                "Practice common Japanese conversations for everyday situations.",
                CourseLevel.INTERMEDIATE,
                CourseCategory.CONVERSATION
        );
        conversationPractice.setTotalLessons(22);
        conversationPractice.setEstimatedHours(18);
        conversationPractice.setImageUrl("https://images.unsplash.com/photo-1551836022-deb4988cc6c0?w=400");

        Course vocabularyBuilder = new Course(
                "Essential Vocabulary Builder",
                "Build your Japanese vocabulary with 1000 most common words.",
                CourseLevel.INTERMEDIATE,
                CourseCategory.VOCABULARY
        );
        vocabularyBuilder.setTotalLessons(40);
        vocabularyBuilder.setEstimatedHours(25);
        vocabularyBuilder.setImageUrl("https://images.unsplash.com/photo-1434030216411-0b793f4b4173?w=400");

        // Advanced Courses
        Course advancedKanji = new Course(
                "Advanced Kanji Mastery",
                "Master complex kanji characters and their various readings.",
                CourseLevel.ADVANCED,
                CourseCategory.KANJI
        );
        advancedKanji.setTotalLessons(50);
        advancedKanji.setEstimatedHours(40);
        advancedKanji.setImageUrl("https://images.unsplash.com/photo-1528164344705-47542687000d?w=400");

        Course businessJapanese = new Course(
                "Business Japanese",
                "Learn formal Japanese for professional and business contexts.",
                CourseLevel.ADVANCED,
                CourseCategory.CONVERSATION
        );
        businessJapanese.setTotalLessons(35);
        businessJapanese.setEstimatedHours(30);
        businessJapanese.setImageUrl("https://images.unsplash.com/photo-1497032628192-86f99bcd76bc?w=400");

        Course japaneseCulture = new Course(
                "Japanese Culture & Traditions",
                "Understand Japanese culture, customs, and traditional practices.",
                CourseLevel.ADVANCED,
                CourseCategory.CULTURE
        );
        japaneseCulture.setTotalLessons(28);
        japaneseCulture.setEstimatedHours(22);
        japaneseCulture.setImageUrl("https://images.unsplash.com/photo-1545569341-9eb8b30979d9?w=400");

        // Save all courses
        courseRepository.save(hiraganaBasics);
        courseRepository.save(katakanaBasics);
        courseRepository.save(basicGrammar);
        courseRepository.save(kanjiIntro);
        courseRepository.save(conversationPractice);
        courseRepository.save(vocabularyBuilder);
        courseRepository.save(advancedKanji);
        courseRepository.save(businessJapanese);
        courseRepository.save(japaneseCulture);

        System.out.println("Sample courses initialized successfully!");
    }
}
