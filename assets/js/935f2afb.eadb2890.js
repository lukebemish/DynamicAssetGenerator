"use strict";(self.webpackChunkdynamic_asset_generator_wiki=self.webpackChunkdynamic_asset_generator_wiki||[]).push([[53],{1109:e=>{e.exports=JSON.parse('{"pluginId":"default","version":"current","label":"Next","banner":null,"badge":false,"noIndex":false,"className":"docs-version-current","isLast":true,"docsSidebars":{"tutorialSidebar":[{"type":"link","label":"Dynamic Asset Generator","href":"/DynamicAssetGenerator/","docId":"Dynamic Asset Generator"},{"type":"category","label":"JSON Generators","collapsible":true,"collapsed":true,"items":[{"type":"category","label":"Texture Generation","collapsible":true,"collapsed":true,"items":[{"type":"category","label":"Mask Sources","collapsible":true,"collapsed":true,"items":[{"type":"link","label":"Add Mask Source","href":"/DynamicAssetGenerator/json/texsources/masks/add","docId":"json/texsources/masks/add"},{"type":"link","label":"Channel Mask Source","href":"/DynamicAssetGenerator/json/texsources/masks/channel","docId":"json/texsources/masks/channel"},{"type":"link","label":"Cutoff Mask Source","href":"/DynamicAssetGenerator/json/texsources/masks/cutoff","docId":"json/texsources/masks/cutoff"},{"type":"link","label":"Edge Mask Source","href":"/DynamicAssetGenerator/json/texsources/masks/edge","docId":"json/texsources/masks/edge"},{"type":"link","label":"Grow Mask Source","href":"/DynamicAssetGenerator/json/texsources/masks/grow","docId":"json/texsources/masks/grow"},{"type":"link","label":"Invert Mask Source","href":"/DynamicAssetGenerator/json/texsources/masks/invert","docId":"json/texsources/masks/invert"},{"type":"link","label":"Multiply Mask Source","href":"/DynamicAssetGenerator/json/texsources/masks/multiply","docId":"json/texsources/masks/multiply"}],"href":"/DynamicAssetGenerator/json/texsources/masks/category"},{"type":"link","label":"Color Source","href":"/DynamicAssetGenerator/json/texsources/color","docId":"json/texsources/color"},{"type":"link","label":"Crop Source","href":"/DynamicAssetGenerator/json/texsources/crop","docId":"json/texsources/crop"},{"type":"link","label":"Error Source","href":"/DynamicAssetGenerator/json/texsources/error","docId":"json/texsources/error"},{"type":"link","label":"Fallback Source","href":"/DynamicAssetGenerator/json/texsources/fallback","docId":"json/texsources/fallback"},{"type":"link","label":"Texture File Source","href":"/DynamicAssetGenerator/json/texsources/file","docId":"json/texsources/file"},{"type":"link","label":"Foreground Transfer Source","href":"/DynamicAssetGenerator/json/texsources/foreground_transfer","docId":"json/texsources/foreground_transfer"},{"type":"link","label":"Mask Source","href":"/DynamicAssetGenerator/json/texsources/mask","docId":"json/texsources/mask"},{"type":"link","label":"Overlay Source","href":"/DynamicAssetGenerator/json/texsources/overlay","docId":"json/texsources/overlay"},{"type":"link","label":"Combined Paletted Source","href":"/DynamicAssetGenerator/json/texsources/palette_combined","docId":"json/texsources/palette_combined"},{"type":"link","label":"Palette Spread Source","href":"/DynamicAssetGenerator/json/texsources/palette_spread","docId":"json/texsources/palette_spread"},{"type":"link","label":"Shadowed Source","href":"/DynamicAssetGenerator/json/texsources/shadowed","docId":"json/texsources/shadowed"},{"type":"link","label":"Animation Splitter","href":"/DynamicAssetGenerator/json/texsources/splitter","docId":"json/texsources/splitter"},{"type":"link","label":"Transform Source","href":"/DynamicAssetGenerator/json/texsources/transform","docId":"json/texsources/transform"}],"href":"/DynamicAssetGenerator/json/texsources/category"},{"type":"link","label":"Texture Metadata Generation","href":"/DynamicAssetGenerator/json/metadata","docId":"json/metadata"},{"type":"link","label":"Dummy Generator","href":"/DynamicAssetGenerator/json/dummy","docId":"json/dummy"}],"href":"/DynamicAssetGenerator/json/category"},{"type":"category","label":"Java API","collapsible":true,"collapsed":true,"items":[{"type":"link","label":"General Concepts","href":"/DynamicAssetGenerator/java/concepts","docId":"java/concepts"},{"type":"link","label":"Texture Generation","href":"/DynamicAssetGenerator/java/textures","docId":"java/textures"}],"href":"/DynamicAssetGenerator/java/category"}]},"docs":{"Dynamic Asset Generator":{"id":"Dynamic Asset Generator","title":"Dynamic Asset Generator","description":"Dynamic Asset Generator (or DynAssetGen) is a minecraft mod meant to help dynamically generate assets and data at runtime, as opposed to creating them manually or when the mod is compiled. Some possible uses include:","sidebar":"tutorialSidebar"},"java/category":{"id":"java/category","title":"Java API","description":"While these docs will contain examples and explanations, the javadocs for DynAssetGen are also a good source for information on the java API; while they are not complete, I am continually working to expand how much of","sidebar":"tutorialSidebar"},"java/concepts":{"id":"java/concepts","title":"General Concepts","description":"Resources and data are provided through a ResourceCache; DynAssetGen has built in implementations of ResourceCache for","sidebar":"tutorialSidebar"},"java/textures":{"id":"java/textures","title":"Texture Generation","description":"All texture generators available through the JSON system are also available in code; they are located in","sidebar":"tutorialSidebar"},"json/category":{"id":"json/category","title":"JSON Generators","description":"Dynamic Asset Generator can be used to add or overwrite assets or data using JSON files. These files live in assets//dynamicassetgenerator or data//dynamicassetgenerator, and can be placed under any namespace and within subfolders. Their general format is as follows:","sidebar":"tutorialSidebar"},"json/dummy":{"id":"json/dummy","title":"Dummy Generator","description":"Generator IDdummy","sidebar":"tutorialSidebar"},"json/metadata":{"id":"json/metadata","title":"Texture Metadata Generation","description":"Generator IDtexture_meta","sidebar":"tutorialSidebar"},"json/texsources/category":{"id":"json/texsources/category","title":"Texture Generation","description":"Generator IDtexture","sidebar":"tutorialSidebar"},"json/texsources/color":{"id":"json/texsources/color","title":"Color Source","description":"Source Type IDcolor","sidebar":"tutorialSidebar"},"json/texsources/crop":{"id":"json/texsources/crop","title":"Crop Source","description":"Source Type IDcrop","sidebar":"tutorialSidebar"},"json/texsources/error":{"id":"json/texsources/error","title":"Error Source","description":"Source Type IDpalette_spread","sidebar":"tutorialSidebar"},"json/texsources/fallback":{"id":"json/texsources/fallback","title":"Fallback Source","description":"Source Type IDfallback","sidebar":"tutorialSidebar"},"json/texsources/file":{"id":"json/texsources/file","title":"Texture File Source","description":"Source Type IDtexture","sidebar":"tutorialSidebar"},"json/texsources/foreground_transfer":{"id":"json/texsources/foreground_transfer","title":"Foreground Transfer Source","description":"Source Type IDforeground_transfer","sidebar":"tutorialSidebar"},"json/texsources/mask":{"id":"json/texsources/mask","title":"Mask Source","description":"Source Type IDmask","sidebar":"tutorialSidebar"},"json/texsources/masks/add":{"id":"json/texsources/masks/add","title":"Add Mask Source","description":"Source Type IDmask/add","sidebar":"tutorialSidebar"},"json/texsources/masks/category":{"id":"json/texsources/masks/category","title":"Mask Sources","description":"Source Type IDmask","sidebar":"tutorialSidebar"},"json/texsources/masks/channel":{"id":"json/texsources/masks/channel","title":"Channel Mask Source","description":"Source Type IDmask/channel","sidebar":"tutorialSidebar"},"json/texsources/masks/cutoff":{"id":"json/texsources/masks/cutoff","title":"Cutoff Mask Source","description":"Source Type IDmask/cutoff","sidebar":"tutorialSidebar"},"json/texsources/masks/edge":{"id":"json/texsources/masks/edge","title":"Edge Mask Source","description":"Source Type IDmask/edge","sidebar":"tutorialSidebar"},"json/texsources/masks/grow":{"id":"json/texsources/masks/grow","title":"Grow Mask Source","description":"Source Type IDmask/grow","sidebar":"tutorialSidebar"},"json/texsources/masks/invert":{"id":"json/texsources/masks/invert","title":"Invert Mask Source","description":"Source Type IDmask/invert","sidebar":"tutorialSidebar"},"json/texsources/masks/multiply":{"id":"json/texsources/masks/multiply","title":"Multiply Mask Source","description":"Source Type IDmask/multiply","sidebar":"tutorialSidebar"},"json/texsources/overlay":{"id":"json/texsources/overlay","title":"Overlay Source","description":"Source Type IDoverlay","sidebar":"tutorialSidebar"},"json/texsources/palette_combined":{"id":"json/texsources/palette_combined","title":"Combined Paletted Source","description":"Source Type IDpalette_combined","sidebar":"tutorialSidebar"},"json/texsources/palette_spread":{"id":"json/texsources/palette_spread","title":"Palette Spread Source","description":"Source Type IDpalette_spread","sidebar":"tutorialSidebar"},"json/texsources/shadowed":{"id":"json/texsources/shadowed","title":"Shadowed Source","description":"Source Type IDpalette_spread","sidebar":"tutorialSidebar"},"json/texsources/splitter":{"id":"json/texsources/splitter","title":"Animation Splitter","description":"Source Type IDanimationsplitter and dynamicassetgenerator:framecapture","sidebar":"tutorialSidebar"},"json/texsources/transform":{"id":"json/texsources/transform","title":"Transform Source","description":"Source Type IDtransform","sidebar":"tutorialSidebar"}}}')}}]);