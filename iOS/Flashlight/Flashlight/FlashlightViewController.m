//
//  FlashlightViewController.m
//  Flashlight
//
//  Created by Tom LePage on 8/22/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "FlashlightViewController.h"
#import <AVFoundation/AVFoundation.h>
//#import <AVFoundation/AVCaptureDevice.h>

@interface FlashlightViewController ()

@end

@implementation FlashlightViewController
@synthesize indicator;
@synthesize onButton;
@synthesize offButton;

- (void)viewDidLoad
{
    [super viewDidLoad];
	_isOn = NO;
    
    AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
    
    // check and see if the device has a flashlight on it (iPhone 4+)
    if ([device hasTorch] == NO)
    {
        //Create UIAlertView alert
        UIAlertView *alert;
        alert = [[UIAlertView alloc] initWithTitle:@"Device Not Supported" message:@"Sorry, but your device does not support a flashlight" delegate:self cancelButtonTitle:@"Cancel" otherButtonTitles: nil];
        
        //After some time
        [alert dismissWithClickedButtonIndex:0 animated:TRUE];
        
        [onButton setEnabled:NO];
        [offButton setEnabled:NO];
    }
}

- (void)viewDidUnload
{
    [self setOnButton:nil];
    [self setOffButton:nil];
    [self setIndicator:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return YES;
}

- (void)toggleFlashlight
{
    AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
    
    if (device.torchMode == AVCaptureTorchModeOff)
    {
        [device lockForConfiguration:nil];
        [device setTorchMode:AVCaptureTorchModeOn];
        [device unlockForConfiguration];
    }
    else if (device.torchMode == AVCaptureTorchModeOn)
    {
        [device lockForConfiguration:nil];
        [device setTorchMode:AVCaptureTorchModeOff];
        [device unlockForConfiguration];
    }
}

- (IBAction)turnOn:(id)sender
{
    if (!_isOn)
    {
        _isOn = YES;
        indicator.text = @"Flashlight On";
        [self toggleFlashlight];
    }
}

- (IBAction)turnOff:(id)sender
{
    if (_isOn)
    {
        _isOn = NO;
        indicator.text = @"Flashlight Off";
        [self toggleFlashlight];
    }
}

@end
