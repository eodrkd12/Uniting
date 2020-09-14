//
//  ReviewData.swift
//  IOS
//
//  Created by 김세현 on 2020/09/08.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ReviewData : Codable {
    var review_id : Int
    var user_id : String
    var user_nickname : String
    var review_content : String
    var review_date : String
    var review_point : Int
    var image_url : String?
}
