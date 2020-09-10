package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test

class StatisticsUtilsTest {
    @Test
    fun getActiveAndCompleteStats_noCompleted_returnHundredZero() {
        // Create tasks
        val tasks = listOf<Task>(
                Task("tittle", "desc", isCompleted = false)
        )

        val result = getActiveAndCompletedStats(tasks)
        assertThat(result.activeTasksPercent, `is`(100f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompleteStats_emptyList_returnZeroZero() {
        // Create tasks
        val tasks = listOf<Task>()

        val result = getActiveAndCompletedStats(tasks)
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompleteStats_noActive_returnZeroHundred() {
        // Create tasks
        val tasks = listOf<Task>(
                Task("title", "desc", isCompleted = true)
        )

        val result = getActiveAndCompletedStats(tasks)
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(100f))
    }

    @Test
    fun getActiveAndCompleteStats_both_returnSixtyForty() {
        // Create tasks
        val tasks = listOf<Task>(
                Task("first", "first task", isCompleted = false),
                Task("second", "second task", isCompleted = true),
                Task("third", "third task", isCompleted = false),
                Task("fourth", "fourth task", isCompleted = true),
                Task("fifth", "fifth task", isCompleted = false)
        )

        val result = getActiveAndCompletedStats(tasks)
        assertThat(result.activeTasksPercent, `is`(60f))
        assertThat(result.completedTasksPercent, `is`(40f))
    }

    @Test
    fun getActiveAndCompleteStats_null_returnZeroZero() {
        // Create tasks
        val tasks = null

        val result = getActiveAndCompletedStats(tasks)
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }
}