//
//  File.swift
//  IOS
//
//  Created by 김세현 on 2020/09/08.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import class Kingfisher.KingfisherManager

class ImageManager : ObservableObject {
    static var shared = ImageManager()
    
    func loadImage(url: String, callback: @escaping (UIImage) -> Void){
        KingfisherManager.shared.retrieveImage(with: URL(string: url)!, options: nil, progressBlock: nil, completionHandler: { image, error, cacheType, imageURL in
            
            callback(image!)
        })
    }
}
