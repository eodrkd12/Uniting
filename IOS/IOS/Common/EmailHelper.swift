//
//  File.swift
//  IOS
//
//  Created by 김세현 on 2020/11/05.
//  Copyright © 2020 KSH. All rights reserved.
//

import Foundation
import MessageUI

class EmailHelper: NSObject, MFMailComposeViewControllerDelegate {
    public static let shared = EmailHelper()
        private override init() {
            //
        }
        
        func sendEmail(subject:String, body:String, to:String){
            if !MFMailComposeViewController.canSendMail() {
                print("No mail account found")
                return 
            }
            
            let picker = MFMailComposeViewController()
            
            picker.setSubject(subject)
            picker.setMessageBody(body, isHTML: true)
            picker.setToRecipients([to])
            picker.mailComposeDelegate = self
            
            EmailHelper.getRootViewController()?.present(picker, animated: true, completion: nil)
        }
        
        func mailComposeController(_ controller: MFMailComposeViewController, didFinishWith result: MFMailComposeResult, error: Error?) {
            EmailHelper.getRootViewController()?.dismiss(animated: true, completion: nil)
        }
        
        static func getRootViewController() -> UIViewController? {
            (UIApplication.shared.connectedScenes.first?.delegate as? SceneDelegate)?.window?.rootViewController
        }
}
