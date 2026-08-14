package work.jsfr.uuidgenerator.ui.uuid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import work.jsfr.uuidgenerator.utils.UuidEngine

class UuidViewModel : ViewModel() {

    private val _currentUuid = MutableLiveData<String>()
    val currentUuid: LiveData<String> = _currentUuid

    private val _batchUuids = MutableLiveData<List<String>>()
    val batchUuids: LiveData<List<String>> = _batchUuids

    // State properties
    private var version: String = "v4"
    private var isUppercase: Boolean = false
    private var removeHyphens: Boolean = false
    private var batchCount: Int = 1

    // Expose generatedUuid for compatibility
    val generatedUuid: LiveData<String> = _currentUuid

    init {
        generateSingle()
    }

    fun setVersion(version: String) {
        this.version = version
    }

    fun setUppercase(enabled: Boolean) {
        this.isUppercase = enabled
    }

    fun setRemoveHyphens(enabled: Boolean) {
        this.removeHyphens = enabled
    }

    fun setBatchCount(count: Int) {
        this.batchCount = count
    }

    fun getVersion(): String = version
    fun isUppercase(): Boolean = isUppercase
    fun isRemoveHyphens(): Boolean = removeHyphens
    fun getBatchCount(): Int = batchCount

    fun generateSingle() {
        val uuid = if (version.equals("v7", ignoreCase = true)) {
            UuidEngine.generateV7(isUppercase, removeHyphens)
        } else {
            UuidEngine.generateV4(isUppercase, removeHyphens)
        }
        _currentUuid.value = uuid
    }

    fun generateBatch() {
        val uuids = UuidEngine.generateBatch(batchCount, version, isUppercase, removeHyphens)
        _batchUuids.value = uuids
    }

    fun generateUuid() {
        generateSingle()
    }
}