"use strict";(self.webpackChunkdynamic_asset_generator_wiki=self.webpackChunkdynamic_asset_generator_wiki||[]).push([[428],{3905:(e,t,n)=>{n.d(t,{Zo:()=>u,kt:()=>h});var r=n(7294);function a(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function o(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function i(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?o(Object(n),!0).forEach((function(t){a(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):o(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function c(e,t){if(null==e)return{};var n,r,a=function(e,t){if(null==e)return{};var n,r,a={},o=Object.keys(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||(a[n]=e[n]);return a}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(a[n]=e[n])}return a}var s=r.createContext({}),l=function(e){var t=r.useContext(s),n=t;return e&&(n="function"==typeof e?e(t):i(i({},t),e)),n},u=function(e){var t=l(e.components);return r.createElement(s.Provider,{value:t},e.children)},p="mdxType",d={inlineCode:"code",wrapper:function(e){var t=e.children;return r.createElement(r.Fragment,{},t)}},m=r.forwardRef((function(e,t){var n=e.components,a=e.mdxType,o=e.originalType,s=e.parentName,u=c(e,["components","mdxType","originalType","parentName"]),p=l(n),m=a,h=p["".concat(s,".").concat(m)]||p[m]||d[m]||o;return n?r.createElement(h,i(i({ref:t},u),{},{components:n})):r.createElement(h,i({ref:t},u))}));function h(e,t){var n=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var o=n.length,i=new Array(o);i[0]=m;var c={};for(var s in t)hasOwnProperty.call(t,s)&&(c[s]=t[s]);c.originalType=e,c[p]="string"==typeof e?e:a,i[1]=c;for(var l=2;l<o;l++)i[l]=n[l];return r.createElement.apply(null,i)}return r.createElement.apply(null,n)}m.displayName="MDXCreateElement"},6479:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>s,contentTitle:()=>i,default:()=>d,frontMatter:()=>o,metadata:()=>c,toc:()=>l});var r=n(7462),a=(n(7294),n(3905));const o={sidebar_position:1},i="General Concepts",c={unversionedId:"java/concepts",id:"java/concepts",title:"General Concepts",description:"Resources and data are provided through a ResourceCache; DynAssetGen has built in implementations of ResourceCache for",source:"@site/docs/java/concepts.mdx",sourceDirName:"java",slug:"/java/concepts",permalink:"/DynamicAssetGenerator/java/concepts",draft:!1,editUrl:"https://github.com/lukebemish/DynamicAssetGenerator/tree/docs/docs/java/concepts.mdx",tags:[],version:"current",sidebarPosition:1,frontMatter:{sidebar_position:1},sidebar:"tutorialSidebar",previous:{title:"Java API",permalink:"/DynamicAssetGenerator/java/category"}},s={},l=[],u={toc:l},p="wrapper";function d(e){let{components:t,...n}=e;return(0,a.kt)(p,(0,r.Z)({},u,n,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h1",{id:"general-concepts"},"General Concepts"),(0,a.kt)("p",null,"Resources and data are provided through a ",(0,a.kt)("inlineCode",{parentName:"p"},"ResourceCache"),"; DynAssetGen has built in implementations of ",(0,a.kt)("inlineCode",{parentName:"p"},"ResourceCache")," for\ngenerating assets and data. Resource caches have an identifier associated with them, and can be registered with ",(0,a.kt)("inlineCode",{parentName:"p"},"ResourceCache.register"),". For instance, to register a data cache:"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-java"},'public static final DataResourceCache DATA_CACHE =\n    ResourceCache.register(new DataResourceCache(new ResourceLocation("my_mod", "data")));\n')),(0,a.kt)("p",null,(0,a.kt)("inlineCode",{parentName:"p"},"AssetResourceCache")," is a similar built-in implementation for client resources."),(0,a.kt)("p",null,"Resource generation is structured around several core concepts:"),(0,a.kt)("ul",null,(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("inlineCode",{parentName:"li"},"ResourceGenerationContext"),", an object which holds information about the current resource generation environment"),(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("inlineCode",{parentName:"li"},"IInputStreamSource"),", an object which can take a target file resource location and a generation context, and supplies an\n",(0,a.kt)("inlineCode",{parentName:"li"},"IoSupplier<InputStream>")," which can be used to read the resource data."),(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("inlineCode",{parentName:"li"},"IPathAwareInputStreamSource"),", a ",(0,a.kt)("inlineCode",{parentName:"li"},"IInputStreamSource")," which is aware of which locations it can provide data for."),(0,a.kt)("li",{parentName:"ul"},"Reset listeners, callbacks which are invoked when a resource cache is reset. These should be used to invalidate any sort of\ncaching")),(0,a.kt)("p",null,(0,a.kt)("inlineCode",{parentName:"p"},"ResourceCache"),' contains a number of methods for adding input stream sources; these methods all\ninternally delegate to the "lazy" method which accepts a ',(0,a.kt)("inlineCode",{parentName:"p"},"Supplier<? extends IPathAwareInputStreamSource>")," and does not\nevaluate it until resources are requested from the cache. This means that a ",(0,a.kt)("inlineCode",{parentName:"p"},"IPathAwareInputStreamSource")," can safely access\nexisting resources or data both when generating resources and when determining which resources it can provide."),(0,a.kt)("p",null,"Resources and data from other sources (the base game, resource packs, other mods, etc.) can be accessed through\n",(0,a.kt)("inlineCode",{parentName:"p"},"ServerPrePackRepository")," and ",(0,a.kt)("inlineCode",{parentName:"p"},"ClientPrePackRepository")," respectively. Note that there are separate methods for getting a\nsingle resource, such as a texture or model, and getting all resources with a given location, such as tags."))}d.isMDXComponent=!0}}]);