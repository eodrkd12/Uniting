//
//  CafeteriaData.swift
//  IOS
//
//  Created by 김세현 on 2020/09/02.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct CafeteriaList : Codable{
    var koreanFood : [CafeteriaPreview]
    var japaneseFood : [CafeteriaPreview]
    var chineseFood : [CafeteriaPreview]
    var westernFood : [CafeteriaPreview]
    var fastFood : [CafeteriaPreview]
}

struct CafeteriaPreview : Codable{
    var cafe_no : Int
    var cafe_name : String
    var star_point : Double?
    var cafe_thumbnail : String?
}

struct Cafeteria : Codable {
    var inform : CafeteriaInform
    var menu : [Menu]
    var review : [Review]
}

struct CafeteriaInform : Codable {
    var cafe_address : String
    var cafe_name : String
    var cafe_phone : String
    var cafe_bizhour : String
    var cafe_mapx : Double
    var cafe_mapy : Double
    
}

struct Menu : Codable {
    var menu_title : String?
    var menu_price : String?
}

struct Review : Codable {
    var review_no : Int
    var user_id : String
    var user_nickname : String
    var review_content : String
    var review_date : String
    var review_point : Int
    var image_url : String
}
