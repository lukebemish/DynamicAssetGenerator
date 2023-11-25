"use strict";(self.webpackChunkdynamic_asset_generator_wiki=self.webpackChunkdynamic_asset_generator_wiki||[]).push([[427],{3905:(e,t,n)=>{n.d(t,{Zo:()=>c,kt:()=>f});var r=n(7294);function a(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function o(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function i(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?o(Object(n),!0).forEach((function(t){a(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):o(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function p(e,t){if(null==e)return{};var n,r,a=function(e,t){if(null==e)return{};var n,r,a={},o=Object.keys(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||(a[n]=e[n]);return a}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(a[n]=e[n])}return a}var s=r.createContext({}),l=function(e){var t=r.useContext(s),n=t;return e&&(n="function"==typeof e?e(t):i(i({},t),e)),n},c=function(e){var t=l(e.components);return r.createElement(s.Provider,{value:t},e.children)},u="mdxType",m={inlineCode:"code",wrapper:function(e){var t=e.children;return r.createElement(r.Fragment,{},t)}},d=r.forwardRef((function(e,t){var n=e.components,a=e.mdxType,o=e.originalType,s=e.parentName,c=p(e,["components","mdxType","originalType","parentName"]),u=l(n),d=a,f=u["".concat(s,".").concat(d)]||u[d]||m[d]||o;return n?r.createElement(f,i(i({ref:t},c),{},{components:n})):r.createElement(f,i({ref:t},c))}));function f(e,t){var n=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var o=n.length,i=new Array(o);i[0]=d;var p={};for(var s in t)hasOwnProperty.call(t,s)&&(p[s]=t[s]);p.originalType=e,p[u]="string"==typeof e?e:a,i[1]=p;for(var l=2;l<o;l++)i[l]=n[l];return r.createElement.apply(null,i)}return r.createElement.apply(null,n)}d.displayName="MDXCreateElement"},8399:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>s,contentTitle:()=>i,default:()=>m,frontMatter:()=>o,metadata:()=>p,toc:()=>l});var r=n(7462),a=(n(7294),n(3905));const o={sidebar_position:2},i="Texture Metadata Generation",p={unversionedId:"json/metadata",id:"json/metadata",title:"Texture Metadata Generation",description:"Generator IDtexture_meta",source:"@site/docs/json/metadata.md",sourceDirName:"json",slug:"/json/metadata",permalink:"/DynamicAssetGenerator/json/metadata",draft:!1,editUrl:"https://github.com/lukebemishprojects/DynamicAssetGenerator/tree/docs/docs/json/metadata.md",tags:[],version:"current",sidebarPosition:2,frontMatter:{sidebar_position:2},sidebar:"tutorialSidebar",previous:{title:"Transform Source",permalink:"/DynamicAssetGenerator/json/texsources/transform"},next:{title:"Dummy Generator",permalink:"/DynamicAssetGenerator/json/dummy"}},s={},l=[],c={toc:l},u="wrapper";function m(e){let{components:t,...n}=e;return(0,a.kt)(u,(0,r.Z)({},c,n,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h1",{id:"texture-metadata-generation"},"Texture Metadata Generation"),(0,a.kt)("p",null,"Generator ID: ",(0,a.kt)("inlineCode",{parentName:"p"},"dynamic_asset_generator:texture_meta")),(0,a.kt)("p",null,"Format:"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-json"},'{\n    "type" : "dynamic_asset_generator:texture_meta",\n    "sources" : [\n        "namespace:texture_path1",\n        "namespace:texture_path2"\n    ]\n    "output_location" : "namespace:texture_path",\n    "animation" : {\n        "frametime" : 300,\n        "interpolate" : true,\n        "width" : 16,\n        "height" : 16,\n    },\n    "villager" : {\n        "hat" : "partial"\n    },\n    "texture" : {\n        "blur" : true,\n        "clamp" : false\n    }\n}\n')),(0,a.kt)("p",null,"This generator generates a ",(0,a.kt)("inlineCode",{parentName:"p"},".png.mcmeta")," file for a texture at ",(0,a.kt)("inlineCode",{parentName:"p"},"output_location")," based on a series of other textures specified in ",(0,a.kt)("inlineCode",{parentName:"p"},"sources"),"."),(0,a.kt)("p",null,(0,a.kt)("inlineCode",{parentName:"p"},"animation"),", ",(0,a.kt)("inlineCode",{parentName:"p"},"villager"),", and ",(0,a.kt)("inlineCode",{parentName:"p"},"texture"),", along with all their arguments, are optional; if not provided, they will either be excluded if appropriate of inherited from the source textures if present."),(0,a.kt)("p",null,"The arguments of ",(0,a.kt)("inlineCode",{parentName:"p"},"villager"),", ",(0,a.kt)("inlineCode",{parentName:"p"},"animation"),", and ",(0,a.kt)("inlineCode",{parentName:"p"},"texture")," allow the corresponding arguments in the ",(0,a.kt)("inlineCode",{parentName:"p"},".png.mcmeta")," to be overridden instead of inherited."))}m.isMDXComponent=!0}}]);