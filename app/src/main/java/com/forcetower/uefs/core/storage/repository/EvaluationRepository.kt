package com.forcetower.uefs.core.storage.repository

import androidx.lifecycle.LiveData
import com.forcetower.uefs.AppExecutors
import com.forcetower.uefs.core.model.service.EvaluationDiscipline
import com.forcetower.uefs.core.model.service.EvaluationHomeTopic
import com.forcetower.uefs.core.model.service.EvaluationTeacher
import com.forcetower.uefs.core.model.unes.Question
import com.forcetower.uefs.core.storage.network.UService
import com.forcetower.uefs.core.storage.network.adapter.asLiveData
import com.forcetower.uefs.core.storage.resource.NetworkOnlyResource
import com.forcetower.uefs.core.storage.resource.Resource
import javax.inject.Inject

class EvaluationRepository @Inject constructor(
    private val service: UService,
    private val executors: AppExecutors
) {
    fun getTrendingList(): LiveData<Resource<List<EvaluationHomeTopic>>> {
        return object : NetworkOnlyResource<List<EvaluationHomeTopic>>(executors) {
            override fun createCall() = service.getEvaluationTopics().asLiveData()
            override fun saveCallResult(value: List<EvaluationHomeTopic>) = Unit
        }.asLiveData()
    }

    fun getDiscipline(department: String, code: String): LiveData<Resource<EvaluationDiscipline>> {
        return object : NetworkOnlyResource<EvaluationDiscipline>(executors) {
            override fun createCall() = service.getEvaluationDiscipline(department, code).asLiveData()
            override fun saveCallResult(value: EvaluationDiscipline) = Unit
        }.asLiveData()
    }

    fun getTeacherById(teacherId: Long): LiveData<Resource<EvaluationTeacher>> {
        return object : NetworkOnlyResource<EvaluationTeacher>(executors) {
            override fun createCall() = service.getTeacherById(teacherId).asLiveData()
            override fun saveCallResult(value: EvaluationTeacher) = Unit
        }.asLiveData()
    }

    fun getQuestionsForTeacher(): LiveData<Resource<List<Question>>> {
        return object : NetworkOnlyResource<List<Question>>(executors) {
            override fun createCall() = service.getQuestionsForTeachers().asLiveData()
            override fun saveCallResult(value: List<Question>) = Unit
        }.asLiveData()
    }
}
