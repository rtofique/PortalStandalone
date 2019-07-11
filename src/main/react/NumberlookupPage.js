import React from 'react';
import NumberForm from "./NumberForm";
import NumberLookupOutput from "./NumberLookupOutput";
import {Navigation, Link, Note, BandwidthProvider, Spacing} from '@bandwidth/shared-components';



export default class NumberlookupPage extends React.Component{

	constructor(props)
	{
		super(props);
		this.handleRequestSubmission = this.handleRequestSubmission.bind(this);
		this.state = {numberInput : '', inputSubmitted: false};
	}

	/**
	 *
	 * @param submission
	 * This function is passed to NumberForm so that request and response are lifted up to this componenent and can thus be passed on to the output dialogue
	 */
	handleRequestSubmission(submission, timestamp)
	{
		this.setState({numberInput : submission, inputSubmitted: true, dateTime:timestamp});
	}




	render()
	{
		const input = this.state.numberInput;
		const inputSubmitted = this.state.inputSubmitted;
		let outputForm;

		if(inputSubmitted)
		{
			outputForm = <NumberLookupOutput output = {input} />;
		}
		else
		{
			outputForm = <NumberLookupOutput output = "" />;
		}

		const NavBar = () => (
				<Navigation>
					<Link.Wrap to ="/">
						<Navigation.Title>Bandwidth Tooling Portal</Navigation.Title>
					</Link.Wrap>
				</Navigation>
		);


		return (
				<BandwidthProvider>
					<div>
						<NavBar />
						<Spacing size = "xs">
							<h1>Number Lookup</h1>
						</Spacing>
						<Spacing size = "xs">
							<Note>This tools returns all the records associated with an NPA-NXX-Block number in the Bandwidth databases. You can enter multiple values separated by a comma, or upload a csv file.
								If a phone number is not found, it will default to searching for that number's NPA-NXX value with an 'A' block number.</Note>
						</Spacing>

						<Spacing size = "xs">
							<NumberForm onRequestSubmission = {this.handleRequestSubmission} />
						</Spacing>


						<Spacing size = "md">
							<NumberLookupOutput output = {input} timestamp = {this.state.dateTime} />
						</Spacing>

					</div>
				</BandwidthProvider>
		);
	}
}
