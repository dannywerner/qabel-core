package de.qabel.box.storage

import org.apache.commons.io.FileUtils
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test
import java.io.File
import java.io.IOException
import java.nio.file.Path

class BoxVolumeLocalTest : BoxVolumeTest() {
    private val tempFolder: Path by lazy { createTempDir("longerPrefix").toPath() }

    override val readBackend: StorageReadBackend by lazy { LocalReadBackend(tempFolder) }

    @Throws(IOException::class)
    public override fun setUpVolume() {
        prefix = ""

        volume = BoxVolumeImpl(readBackend,
                LocalWriteBackend(tempFolder),
                keyPair, deviceID, File(System.getProperty("java.io.tmpdir")), "")
        volume2 = BoxVolumeImpl(LocalReadBackend(tempFolder),
                LocalWriteBackend(tempFolder),
                keyPair, deviceID2, File(System.getProperty("java.io.tmpdir")), "")
    }

    @Throws(IOException::class)
    override fun cleanVolume() {
        FileUtils.deleteDirectory(tempFolder.toFile())
    }

    @Test
    @Throws(Exception::class)
    fun rootRefNotChanged() {
        assertThat(volume!!.rootRef, equalTo("300c9c96-03b9-2a4b-39ed-3958bf924011"))
    }
}
