//
//  CafeteriaData.swift
//  IOS
//
//  Created by 김세현 on 2020/09/02.
//  Copyright © 2020 KSH. All rights reserved.
//

import Foundation

struct CafeteriaDataList {
    var items : [CafeteriaData]?
}

struct CafeteriaData : Codable {
    var id : String
    var name : String
    var x : String
    var y : String
    var phone : String
    var imageSrc : String
    var roadAddr : String
    var tags : [String]
    var options : String
    var bizHourInfo : String
}

struct Menu : Codable {
    var name : String?
    var price : String?
}
