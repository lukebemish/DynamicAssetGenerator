MultiplatformModsDotGroovy.make {
    modLoader = 'javafml'
    loaderVersion = '[1,)'
    license = 'LGPL-3.0-or-later'
    issueTrackerUrl = 'https://github.com/lukebemishprojects/DynamicAssetGenerator/issues'

    mod {
        modId = buildProperties.mod_id
        displayName = buildProperties.mod_name
        version = environmentInfo.version
        displayUrl = 'https://github.com/lukebemishprojects/DynamicAssetGenerator'
        contact {
            sources = 'https://github.com/lukebemishprojects/DynamicAssetGenerator'
        }
        author = 'Luke Bemish'
        description = buildProperties.description

        entrypoints {
            client = 'dev.lukebemish.dynamicassetgenerator.impl.fabriquilt.fabric.DynamicAssetGeneratorClientFabric'
            main = 'dev.lukebemish.dynamicassetgenerator.impl.fabriquilt.fabric.DynamicAssetGeneratorFabric'
        }

        dependencies {
            mod 'minecraft', {
                versionRange = ">=${libs.versions.minecraft}"
            }
            onNeoForge {
                neoforge = ">=${libs.versions.neoforge}"
            }
            onFabric {
                mod 'fabricloader', {
                    versionRange = ">=${libs.versions.fabric_loader}"
                }
                mod 'fabric-api', {
                    versionRange = ">=${libs.versions.fabric_api.split(/\+/)[0]}"
                }
            }
        }
    }

    onFabric {
        mixins {
            mixin 'mixin.dynamic_asset_generator.json'
            mixin 'mixin.dynamic_asset_generator_fabriquilt.json'
        }
    }
    onNeoForge {
        mixins {
            mixin 'mixin.dynamic_asset_generator.json'
        }
    }
}
