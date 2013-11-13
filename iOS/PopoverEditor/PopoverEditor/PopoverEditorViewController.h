//
//  PopoverEditorViewController.h
//  PopoverEditor
//
//  Created by Tom LePage on 8/30/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "EditorViewController.h"

@interface PopoverEditorViewController : UIViewController <UIPopoverControllerDelegate>
@property (weak, nonatomic) IBOutlet UILabel *emailLabel;

@end
