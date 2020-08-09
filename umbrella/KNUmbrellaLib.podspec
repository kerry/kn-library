Pod::Spec.new do |spec|
    spec.name                     = 'KNUmbrellaLib'
    spec.version                  = '1.0'
    spec.homepage                 = 'https://github.com/kerry'
    spec.source                   = { :git => "Not Published", :tag => "Cocoapods/#{spec.name}/#{spec.version}" }
    spec.authors                  = ''
    spec.license                  = ''
    spec.summary                  = 'KNUmbrellaLib'

    spec.vendored_frameworks      = "products/xcode-frameworks/#{spec.name}.framework"
    spec.libraries                = "c++"
    spec.module_name              = "#{spec.name}_umbrella"

    spec.pod_target_xcconfig = {
        'KOTLIN_TARGET[sdk=iphonesimulator*]' => 'ios_x64',
        'KOTLIN_TARGET[sdk=iphoneos*]' => 'ios_arm',
        'KOTLIN_TARGET[sdk=macosx*]' => 'macos_x64',
        'ENABLE_KOTLIN_NATIVE_BUILD' => 'DISABLED'
    }

end
