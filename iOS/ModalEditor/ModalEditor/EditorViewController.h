//
//  EditorViewController.h
//  ModalEditor
//
//  Created by Tom LePage on 8/30/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ModalEditorViewController.h" // must do this to access data from other view controller

@interface EditorViewController : UIViewController

@property (weak, nonatomic) IBOutlet UITextField *emailField;

- (IBAction)dismissEditor:(id)sender;

@end
