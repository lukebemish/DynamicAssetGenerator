"use strict";(self.webpackChunkdynamic_asset_generator_wiki=self.webpackChunkdynamic_asset_generator_wiki||[]).push([[808],{3905:(e,t,r)=>{r.d(t,{Zo:()=>u,kt:()=>h});var n=r(7294);function a(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function o(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function i(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?o(Object(r),!0).forEach((function(t){a(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):o(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function c(e,t){if(null==e)return{};var r,n,a=function(e,t){if(null==e)return{};var r,n,a={},o=Object.keys(e);for(n=0;n<o.length;n++)r=o[n],t.indexOf(r)>=0||(a[r]=e[r]);return a}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(n=0;n<o.length;n++)r=o[n],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(a[r]=e[r])}return a}var s=n.createContext({}),l=function(e){var t=n.useContext(s),r=t;return e&&(r="function"==typeof e?e(t):i(i({},t),e)),r},u=function(e){var t=l(e.components);return n.createElement(s.Provider,{value:t},e.children)},p="mdxType",d={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},m=n.forwardRef((function(e,t){var r=e.components,a=e.mdxType,o=e.originalType,s=e.parentName,u=c(e,["components","mdxType","originalType","parentName"]),p=l(r),m=a,h=p["".concat(s,".").concat(m)]||p[m]||d[m]||o;return r?n.createElement(h,i(i({ref:t},u),{},{components:r})):n.createElement(h,i({ref:t},u))}));function h(e,t){var r=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var o=r.length,i=new Array(o);i[0]=m;var c={};for(var s in t)hasOwnProperty.call(t,s)&&(c[s]=t[s]);c.originalType=e,c[p]="string"==typeof e?e:a,i[1]=c;for(var l=2;l<o;l++)i[l]=r[l];return n.createElement.apply(null,i)}return n.createElement.apply(null,r)}m.displayName="MDXCreateElement"},8065:(e,t,r)=>{r.r(t),r.d(t,{assets:()=>s,contentTitle:()=>i,default:()=>d,frontMatter:()=>o,metadata:()=>c,toc:()=>l});var n=r(7462),a=(r(7294),r(3905));const o={},i="Texture Generation",c={unversionedId:"java/textures",id:"java/textures",title:"Texture Generation",description:"All texture generators available through the JSON system are also available in code; they are located in",source:"@site/docs/java/textures.mdx",sourceDirName:"java",slug:"/java/textures",permalink:"/DynamicAssetGenerator/java/textures",draft:!1,editUrl:"https://github.com/lukebemish/DynamicAssetGenerator/tree/docs/docs/java/textures.mdx",tags:[],version:"current",frontMatter:{},sidebar:"tutorialSidebar",previous:{title:"General Concepts",permalink:"/DynamicAssetGenerator/java/concepts"}},s={},l=[{value:"Custom Texture Sources",id:"custom-texture-sources",level:2}],u={toc:l},p="wrapper";function d(e){let{components:t,...r}=e;return(0,a.kt)(p,(0,n.Z)({},u,r,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h1",{id:"texture-generation"},"Texture Generation"),(0,a.kt)("p",null,"All texture generators available through the JSON system are also available in code; they are located in\n",(0,a.kt)("inlineCode",{parentName:"p"},"dev.lukebemish.dynamicassetgenerator.api.client.generators.texsources"),", and are subclasses of ",(0,a.kt)("inlineCode",{parentName:"p"},"TexSource"),". A ",(0,a.kt)("inlineCode",{parentName:"p"},"TexSource"),"\ncan be turned into a ",(0,a.kt)("inlineCode",{parentName:"p"},"PathAwareInputStreamSource")," by wrapping them in a ",(0,a.kt)("inlineCode",{parentName:"p"},"TextureGenerator"),". For example:"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-java"},'public class ClientExample {\n    public static final AssetResourceCache ASSET_CACHE =\n        ResourceCache.register(new AssetResourceCache(new ResourceLocation("my_mod", "assets")));\n    \n    public static void initializeClient() {\n        // This method would be run during client initialization from the appropriate location.\n        ASSET_CACHE.planSource(\n            new TextureGenerator(\n                new ResourceLocation("my_mod","block/my_block"),\n                new TextureReaderSource.Builder().setPath(new ResourceLocation("item/apple")).build()\n            )\n        );\n    }\n}\n')),(0,a.kt)("p",null,"This will register a new texture source that copies the ",(0,a.kt)("inlineCode",{parentName:"p"},"item/apple")," texture to ",(0,a.kt)("inlineCode",{parentName:"p"},"my_mod:block/my_block"),"."),(0,a.kt)("h2",{id:"custom-texture-sources"},"Custom Texture Sources"),(0,a.kt)("p",null,"Custom texture sources can be created by implementing ",(0,a.kt)("inlineCode",{parentName:"p"},"TexSource"),", which requires implementing two methods:"),(0,a.kt)("p",null,(0,a.kt)("inlineCode",{parentName:"p"},"getSupplier")," takes a ",(0,a.kt)("inlineCode",{parentName:"p"},"TexSourceDataHolder")," and a ",(0,a.kt)("inlineCode",{parentName:"p"},"ResourceGenerationContext"),", and returns a ",(0,a.kt)("inlineCode",{parentName:"p"},"IoSupplier<NativeImage>"),", or\n",(0,a.kt)("inlineCode",{parentName:"p"},"null")," if no texture can be constructed. The ",(0,a.kt)("inlineCode",{parentName:"p"},"TexSourceDataHolder"),' is used to hold data that should be passed to "child"\nelements of the texture source, such as the logger, which can be retrieved with ',(0,a.kt)("inlineCode",{parentName:"p"},"TexSourceDataHolder#getLogger"),". Note that\nmaking your source's output depend on other data in this element requires implementing the currently-experimental caching\nAPI."),(0,a.kt)("p",null,(0,a.kt)("inlineCode",{parentName:"p"},"codec")," returns a codec that can be used to serialize or deserialize the texture source. This codec must be registered with\n",(0,a.kt)("inlineCode",{parentName:"p"},"TexSource.register"),", and is used when caching texture sources; a texture source's generated texture must be fully\ndescribable by the information encoded by its codec, unless the experimental caching API is implemented for that texture\nsource."))}d.isMDXComponent=!0}}]);