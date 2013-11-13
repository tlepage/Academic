//
//  DetailViewController.m
//  Homepwner
//
//  Created by Tom LePage on 10/1/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "DetailViewController.h"
#import "BNRItem.h"
#import "BNRImageStore.h"
#import "BNRItemStore.h"
#import "AssetTypePicker.h"

@interface DetailViewController ()

@end

@implementation DetailViewController

@synthesize item = _item;
@synthesize dismissBlock;

- (void)viewDidLoad
{
    [super viewDidLoad];
    //[[self view] setBackgroundColor:[UIColor groupTableViewBackgroundColor]];
    
    UIColor *clr = nil;
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad)
    {
        clr = [UIColor colorWithRed:0.875 green:0.88 blue:0.91 alpha:1];
    }
    else
    {
        clr = [UIColor groupTableViewBackgroundColor];
    }
    
    [[self view] setBackgroundColor:clr];
}

- (void)save:(id)sender
{
    [[self presentingViewController] dismissViewControllerAnimated:YES completion:dismissBlock];
}

- (void)cancel:(id)sender
{
    [[BNRItemStore sharedStore] removeItem:_item];
    [[self presentingViewController] dismissViewControllerAnimated:YES completion:dismissBlock];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    @throw [NSException exceptionWithName:@"Wrong initializer" reason:@"Use initForNewItem:" userInfo:nil];
    
    return nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)io
{
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad)
    {
        return YES;
    }
    else
    {
        return (io == UIInterfaceOrientationPortrait);
    }
}

- (void)setItem:(BNRItem *)item
{
    // Override this so that we can populate the title of the navigation bar
    _item = item;
    [[self navigationItem] setTitle:[item itemName]];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    [nameField setText:[_item itemName]];
    [serialNumberField setText:[_item serialNumber]];
    [valueField setText:[NSString stringWithFormat:@"%d", [_item valueInDollars]]];
    
    // Create a NSDateFormatter that will turn a date into a simple string
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateStyle:NSDateFormatterMediumStyle];
    [dateFormatter setTimeStyle:NSDateFormatterNoStyle];
    
    // Use filtered NSDate object to set dateLabel
    //[dateLabel setText:[dateFormatter stringFromDate:[_item dateCreated]]];
    NSDate *date = [NSDate dateWithTimeIntervalSinceReferenceDate:[_item dateCreated]];
    [dateLabel setText:[dateFormatter stringFromDate:date]];
    
    NSString *imageKey = [_item imageKey];
    
    if (imageKey)
    {
        // Get image for image key
        UIImage *imageToDisplay = [[BNRImageStore sharedStore] imageForKey:imageKey];
        
        // Use that image to put on the screen in imageView
        [imageView setImage:imageToDisplay];
    }
    else
    {
        [imageView setImage:nil];
    }
    
    NSString *typeLabel = [[_item assetType] valueForKey:@"label"];
    if (!typeLabel)
    {
        typeLabel = @"None";
    }
    
    [assetTypeButton setTitle:[NSString stringWithFormat:@"Type: %@", typeLabel] forState:UIControlStateNormal];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    [[self view] endEditing:YES];
    
    [_item setItemName:[nameField text]];
    [_item setSerialNumber:[serialNumberField text]];
    [_item setValueInDollars:[[valueField text] intValue]];
}

- (IBAction)takePicture:(id)sender
{
    if ([imagePickerPopover isPopoverVisible])
    {
        [imagePickerPopover dismissPopoverAnimated:YES];
        imagePickerPopover = nil;
        return;
    }
    
    UIImagePickerController *imagePicker = [[UIImagePickerController alloc] init];
    
    // If our device has a camera, we want to take a picture, otherwise, pick from library
    if ([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera])
    {
        [imagePicker setSourceType:UIImagePickerControllerSourceTypeCamera];
    }
    else
    {
        [imagePicker setSourceType:UIImagePickerControllerSourceTypePhotoLibrary];
    }
    
    [imagePicker setDelegate:self];
    //[self presentViewController:imagePicker animated:YES completion:nil];
    
    // Place the image picker on the screen
    // Check for iPad device before instantiating the popover controller
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad)
    {
        imagePickerPopover = [[UIPopoverController alloc] initWithContentViewController:imagePicker];
        
        [imagePickerPopover setDelegate:self];
        
        // Display the popover controller; sender is the camera bar button
        [imagePickerPopover presentPopoverFromBarButtonItem:sender permittedArrowDirections:UIPopoverArrowDirectionAny animated:YES];
    }
    else
    {
        [self presentViewController:imagePicker animated:YES completion:nil];
    }
}

- (void)popoverControllerDidDismissPopover:(UIPopoverController *)popoverController
{
    NSLog(@"User dismissed popover");
    imagePickerPopover = nil;
}

- (IBAction)backgroundTapped:(id)sender
{
    // This resigns the first responder if user taps on the view itself
    [[self view] endEditing:YES];
}

- (IBAction)showAssetTypePicker:(id)sender
{
    [[self view] endEditing:YES];
    
    AssetTypePicker *assetTypePicker = [[AssetTypePicker alloc] init];
    [assetTypePicker setItem:_item];
    
    [[self navigationController] pushViewController:assetTypePicker animated:YES];
}

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info
{
    NSString *oldKey = [_item imageKey];
    
    // Did the item already have an image?
    if (oldKey)
    {
        // Delete the old image
        [[BNRImageStore sharedStore] deleteImageForKey:oldKey];
    }
    
    // Get picked image from info dictionary
    UIImage *image = [info objectForKey:UIImagePickerControllerOriginalImage];
    
    [_item setThumbnailDataFromImage:image];
    
    // Create a CFUUID object
    CFUUIDRef newUniqueID = CFUUIDCreate(kCFAllocatorDefault);
    
    // Create a string from unique identifier
    CFStringRef newUniqueIDString = CFUUIDCreateString(kCFAllocatorDefault, newUniqueID);
    
    // Use that unique ID to set our item's imageKey
    NSString *key = (__bridge NSString *)newUniqueIDString;
    [_item setImageKey:key];
    
    // Store image in the BNRImageStore with this key
    [[BNRImageStore sharedStore] setImage:image forKey:[_item imageKey]];
    
    // Make sure to release any CF objects
    CFRelease(newUniqueIDString);
    CFRelease(newUniqueID);
    
    // Put that image onto the screen in our image view
    [imageView setImage:image];
    
    // Take the picker off of the screen
    //[self dismissViewControllerAnimated:YES completion:nil];
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone)
    {
        [self dismissViewControllerAnimated:YES completion:nil];
    }
    else
    {
        [imagePickerPopover dismissPopoverAnimated:YES];
        imagePickerPopover = nil;
    }
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
}

- (id)initForNewItem:(BOOL)isNew
{
    self = [super initWithNibName:@"DetailViewController" bundle:nil];
    
    if (self)
    {
        if (isNew)
        {
            UIBarButtonItem *doneItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemDone target:self
                                                                                      action:@selector(save:)];
            [[self navigationItem] setRightBarButtonItem:doneItem];
            
            UIBarButtonItem *cancelItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(cancel:)];
            
            [[self navigationItem] setLeftBarButtonItem:cancelItem];
        }
    }
    
    return self;
}
@end
