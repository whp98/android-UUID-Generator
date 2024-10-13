package work.jsfr.uuidgenerator.ui.uuid

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

class UuidViewModel : ViewModel() {
    val generatedUuid = MutableLiveData<String>()

    init {
        generateUuid()
    }

    fun generateUuid() {
        val uuid = UUID.randomUUID().toString()
        generatedUuid.value = uuid
    }
}