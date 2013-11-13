//
//  FieldButtonFunViewController.h
//  FieldButtonFun
//
//  Created by Tom LePage on 8/28/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface FieldButtonFunViewController : UIViewController

@property (weak, nonatomic) IBOutlet UITextField *thePlace;
@property (weak, nonatomic) IBOutlet UITextField *theVerb;
@property (weak, nonatomic) IBOutlet UITextField *theNumber;
@property (weak, nonatomic) IBOutlet UITextView *theTemplate;
@property (weak, nonatomic) IBOutlet UITextView *theStory;
@property (weak, nonatomic) IBOutlet UIButton *theButton;

- (IBAction)createStory:(id)sender;
- (IBAction)hideKeyboard:(id)sender;

@end
