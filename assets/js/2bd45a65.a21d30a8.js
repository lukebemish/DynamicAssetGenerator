"use strict";(self.webpackChunkdynamic_asset_generator_wiki=self.webpackChunkdynamic_asset_generator_wiki||[]).push([[892],{3905:(e,t,r)=>{r.d(t,{Zo:()=>l,kt:()=>y});var n=r(7294);function a(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function o(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function i(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?o(Object(r),!0).forEach((function(t){a(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):o(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function s(e,t){if(null==e)return{};var r,n,a=function(e,t){if(null==e)return{};var r,n,a={},o=Object.keys(e);for(n=0;n<o.length;n++)r=o[n],t.indexOf(r)>=0||(a[r]=e[r]);return a}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(n=0;n<o.length;n++)r=o[n],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(a[r]=e[r])}return a}var p=n.createContext({}),c=function(e){var t=n.useContext(p),r=t;return e&&(r="function"==typeof e?e(t):i(i({},t),e)),r},l=function(e){var t=c(e.components);return n.createElement(p.Provider,{value:t},e.children)},u="mdxType",m={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},d=n.forwardRef((function(e,t){var r=e.components,a=e.mdxType,o=e.originalType,p=e.parentName,l=s(e,["components","mdxType","originalType","parentName"]),u=c(r),d=a,y=u["".concat(p,".").concat(d)]||u[d]||m[d]||o;return r?n.createElement(y,i(i({ref:t},l),{},{components:r})):n.createElement(y,i({ref:t},l))}));function y(e,t){var r=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var o=r.length,i=new Array(o);i[0]=d;var s={};for(var p in t)hasOwnProperty.call(t,p)&&(s[p]=t[p]);s.originalType=e,s[u]="string"==typeof e?e:a,i[1]=s;for(var c=2;c<o;c++)i[c]=r[c];return n.createElement.apply(null,i)}return n.createElement.apply(null,r)}d.displayName="MDXCreateElement"},1338:(e,t,r)=>{r.r(t),r.d(t,{assets:()=>p,contentTitle:()=>i,default:()=>m,frontMatter:()=>o,metadata:()=>s,toc:()=>c});var n=r(7462),a=(r(7294),r(3905));const o={},i="Crop Source",s={unversionedId:"json/texsources/crop",id:"json/texsources/crop",title:"Crop Source",description:"Source Type IDcrop",source:"@site/docs/json/texsources/crop.md",sourceDirName:"json/texsources",slug:"/json/texsources/crop",permalink:"/DynamicAssetGenerator/json/texsources/crop",draft:!1,editUrl:"https://github.com/lukebemish/DynamicAssetGenerator/tree/docs/docs/json/texsources/crop.md",tags:[],version:"current",frontMatter:{},sidebar:"tutorialSidebar",previous:{title:"Color Source",permalink:"/DynamicAssetGenerator/json/texsources/color"},next:{title:"Error Source",permalink:"/DynamicAssetGenerator/json/texsources/error"}},p={},c=[],l={toc:c},u="wrapper";function m(e){let{components:t,...r}=e;return(0,a.kt)(u,(0,n.Z)({},l,r,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h1",{id:"crop-source"},"Crop Source"),(0,a.kt)("p",null,"Source Type ID: ",(0,a.kt)("inlineCode",{parentName:"p"},"dynamic_asset_generator:crop")),(0,a.kt)("p",null,"Format:"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-json"},'{\n    "type": "dynamic_asset_generator:crop",\n    "input": {   },\n    "total_size": 16,\n    "start_x": 0,\n    "start_y": 0,\n    "size_x": 8,\n    "size_y": 8\n}\n')),(0,a.kt)("ul",null,(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("inlineCode",{parentName:"li"},"input")," a texture source used as an input."),(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("inlineCode",{parentName:"li"},"total_size")," is the expected width of the entire original image. This is used for scaling, and consistency across different resolution resource packs."),(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("inlineCode",{parentName:"li"},"start_x")," and ",(0,a.kt)("inlineCode",{parentName:"li"},"start_y")," are the starting x and y pixels for the output image, counting from the top left. These can be negative."),(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("inlineCode",{parentName:"li"},"size_x")," and ",(0,a.kt)("inlineCode",{parentName:"li"},"size_y")," are the dimensions of the output image in the x and y directions.")),(0,a.kt)("p",null,"All the start and size parameters are relative to the ",(0,a.kt)("inlineCode",{parentName:"p"},"total_size"),". In other words, if the ",(0,a.kt)("inlineCode",{parentName:"p"},"total_size")," is the same as the width of the input image, then the output will be ",(0,a.kt)("inlineCode",{parentName:"p"},"size_x")," by ",(0,a.kt)("inlineCode",{parentName:"p"},"size_y"),", starting at ",(0,a.kt)("inlineCode",{parentName:"p"},"start_x")," and ",(0,a.kt)("inlineCode",{parentName:"p"},"start_y"),"; if the image is twice the ",(0,a.kt)("inlineCode",{parentName:"p"},"total_size"),", then the output will be ",(0,a.kt)("inlineCode",{parentName:"p"},"2*size_x")," by ",(0,a.kt)("inlineCode",{parentName:"p"},"2_size_y"),", starting at ",(0,a.kt)("inlineCode",{parentName:"p"},"2*start_x")," and ",(0,a.kt)("inlineCode",{parentName:"p"},"2*start_y"),"; etc."))}m.isMDXComponent=!0}}]);