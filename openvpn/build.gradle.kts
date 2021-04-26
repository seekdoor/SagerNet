import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.LibraryVariant

/*
 * Copyright (c) 2012-2016 Arne Schwabe
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */

plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(14)
        targetSdkVersion(30)  //'Q'.toInt()
        versionCode = 176
        versionName = "0.7.22"

        externalNativeBuild {
            cmake {
                //arguments = listOf("-DANDROID_TOOLCHAIN=clang",
                //        "-DANDROID_STL=c++_static")
            }
        }
    }


    externalNativeBuild {
        cmake {
            path =File("${projectDir}/src/main/cpp/CMakeLists.txt")
        }
    }

}

var swigcmd = "swig"
// Workaround for Mac OS X since it otherwise does not find swig and I cannot get
// the Exec task to respect the PATH environment :(
if (File("/usr/local/bin/swig").exists())
    swigcmd = "/usr/local/bin/swig"


fun registerGenTask(variantName: String, variantDirName: String): File {
    val baseDir = File(buildDir, "generated/source/ovpn3swig/${variantDirName}")
    val genDir = File(baseDir, "net/openvpn/ovpn3")

    tasks.register<Exec>("generateOpenVPN3Swig${variantName}")
    {

        doFirst {
            mkdir(genDir)
        }
        commandLine(listOf(swigcmd, "-outdir", genDir, "-outcurrentdir", "-c++", "-java", "-package", "net.openvpn.ovpn3",
            "-Isrc/main/cpp/openvpn3/client", "-Isrc/main/cpp/openvpn3/",
            "-o", "${genDir}/ovpncli_wrap.cxx", "-oh", "${genDir}/ovpncli_wrap.h",
            "src/main/cpp/openvpn3/javacli/ovpncli.i"))

    }
    return baseDir
}

android.libraryVariants.all(object : Action<LibraryVariant> {
    override fun execute(variant: LibraryVariant) {
        val sourceDir = registerGenTask(variant.name, variant.baseName.replace("-", "/"))
        val task = tasks.named("generateOpenVPN3Swig${variant.name}").get()

        variant.registerJavaGeneratingTask(task, sourceDir)
    }
})
