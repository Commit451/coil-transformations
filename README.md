# coil-transformations

Image transformations for [Coil](https://github.com/coil-kt/coil).

[![Build](https://github.com/Commit451/coil-transformations/actions/workflows/ci.yml/badge.svg)](https://github.com/Commit451/coil-transformations/actions/workflows/ci.yml) [![Maven Central](https://img.shields.io/maven-central/v/com.commit451.coiltransformations/transformations.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=g:com.commit451.coiltransformations)

## Download

Add the library to your app `build.gradle`:

```kotlin
implementation("com.commit451.coiltransformations:transformations:latest.version.here")
```

and for GPU (native) transformations (relies on [GPUImage](https://github.com/cats-oss/android-gpuimage)):

```kotlin
# Android only
implementation("com.commit451.coiltransformations:transformations-gpu:latest.version.here")
```

and for Google MLKit Face detection transformations:

```kotlin
# Android only
implementation("com.commit451.coiltransformations:transformations-face-detection:latest.version.here")
```

## Supported Transformations

These transformations are already supported in Coil itself (no need for this library):

- CircleCropTransformation
- RoundedCornersTransformation

From the base library (`transformations`):

- ColorFilterTransformation
- CropTransformation (top, center, bottom)
- GrayscaleTransformation
- SquareCropTransformation

**Android only**

- BlurTransformation
- MaskTransformation

From the GPU library (`transformations-gpu`)
**Android only**

- BrightnessFilterTransformation
- ContrastFilterTransformation
- InvertFilterTransformation
- KuwaharaFilterTransformation
- PixelationFilterTransformation
- SepiaFilterTransformation
- SketchFilterTransformation
- SwirlFilterTransformation
- ToonFilterTransformation
- VignetteFilterTransformation

From the face detection library (`transformations-face-detection`)
**Android only**

- CenterOnFaceTransformation

## Quick Start

To load an image into a Composable with transformations:

```kotlin
AsyncImage(
    model = ImageRequest.Builder(LocalPlatformContext.current)
        .data(imageUrl)
        .transformations(GrayscaleTransformation())
        .crossfade(true)
        .build(),
    contentDescription = sample.name,
    contentScale = ContentScale.Crop,
    modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)
        .clip(RoundedCornerShape(8.dp)),
)
```

## Preview

See a preview of the transformations [here](https://github.com/Commit451/coil-transformations/blob/main/preview/preview.md)

## Thanks

Inspired by [Glide Transformations](https://github.com/wasabeef/glide-transformations) by [wasabeef](https://github.com/wasabeef).
Thanks of course to [Coil](https://github.com/coil-kt/coil) contributors for making a great image loading library.

## License

    Copyright 2026 Commit 451

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
