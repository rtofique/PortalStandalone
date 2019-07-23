import React from 'react';
import NumberForm from "./NumberForm";
import NumberLookupOutput from "./NumberLookupOutput";
import {Navigation, Link, HelpText, BandwidthProvider} from '@bandwidth/shared-components';
import styled from "styled-components";
//import { createBrowserHistory } from "history";
//import {withRouter} from "react-router-dom";

/**
 * Main page for the complete app. Encapsulates both the forms and the output sections.
 */

//const history = createBrowserHistory();

const SpacedDiv = styled.div`
		margin-top:3%;
		margin-left:3%;
		margin-right:3%;
	
`;


export default class NumberlookupPage extends React.Component{


	constructor(props)
	{
		super(props);
		this.handleRequestSubmission = this.handleRequestSubmission.bind(this);
		this.toggleLoadingStatus = this.toggleLoadingStatus.bind(this);
		this.state = {responseOutput : '', inputSubmitted: false, isLoading: false, dateTime:'', queryString:''};
	}

	/**
	 *
	 * @param submission the result of the query
	 * @param timestamp the time at which the query was made
	 * Called by the NumberForm child to let the parent know of the response and timestamp so it can relay it to the output components.
	 */
	handleRequestSubmission(response, timestamp, userInput)
	{
		this.setState({responseOutput : response, inputSubmitted: true, dateTime:timestamp, queryString:userInput});
	}

	/**
	 *
	 * @param loadingStatus if the query is still processinr or not
	 * Called by NumberForm to toggle whether a query is still being processed or not. Passed as a prop to NumberLookuOutput so it can decide the output accordingly,
	 */

	toggleLoadingStatus(loadingStatus)
	{
		this.setState({isLoading:loadingStatus});
	}




	render()
	{


		const NavBar = () => (
				<Navigation>
					<Link.Wrap to ="/">
						<Navigation.Title>Bandwidth Tooling Portal</Navigation.Title>
					</Link.Wrap>
				</Navigation>
		);

		//this.state.queryString now has the proper input

		return (
				<BandwidthProvider>
					<div>
						<NavBar />
						<SpacedDiv>
							<h1 style={{color:'#60545b'}}>Rate Center Lookup</h1>
							<HelpText>This tools returns all the records associated with an NPA-NXX-Block number in the Bandwidth databases. You can enter multiple values separated by a comma, or upload a csv file.
							If a phone number is not found, it will default to searching for that number's NPA-NXX value with an 'A' block number.</HelpText>
						<NumberForm onRequestSubmission = {this.handleRequestSubmission} toggleLoading = {this.toggleLoadingStatus} />
						<NumberLookupOutput output = {this.state.responseOutput} timestamp = {this.state.dateTime} isLoading = {this.state.isLoading} queryString = {this.state.queryString} /></SpacedDiv>

					</div>
				</BandwidthProvider>
		);
	}
}

