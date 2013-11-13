//
//  NameEditorViewController.h
//  PhotoWheelPrototype
//
//  Created by Tom LePage on 9/18/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol NameEditorViewControllerDelegate;

@interface NameEditorViewController : UIViewController

@property (strong, nonatomic) IBOutlet UITextField *nameTextField;
@property (strong, nonatomic) id<NameEditorViewControllerDelegate> delegate;
@property (strong, nonatomic) NSIndexPath *indexPath;
@property (nonatomic, copy) NSString *defaultNameText;

- (IBAction)cancel:(id)sender;
- (IBAction)done:(id)sender;

- (id)initWithDefaultNib;

@end

@protocol NameEditorViewControllerDelegate
@optional
- (void) nameEditorViewControllerDidFinish:(NameEditorViewController *)controller;
- (void) nameEditorViewControllerDidCancel:(NameEditorViewController *)controller;
@end
