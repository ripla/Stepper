Field that allows stepping through values via given up/down controls, 
the keyboard, or the mouse wheel. Supports client-side limits for all 
value types.

Implementations for integer, float and date values are provided.

## Getting started

Build project: `./gradlew build` 
 
Run demo application: `./gradlew demo:vaadinRun`

## Usage 

Example:

	final IntStepper stepper = new IntStepper();
	stepper.setValue(1);
	stepper.setImmediate(true);

	stepper.addListener(new Property.ValueChangeListener() {

		public void valueChange(ValueChangeEvent event) {
			stepper.getWindow().showNotification(
				new Date() + " " + event.getProperty().getValue());
		}
	});

## License
Code is licensed under Apache 2.0.
