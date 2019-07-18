import React, { Component } from 'react';
import ResultTable from "./ResultTable";
import {Loader} from '@bandwidth/shared-components';
import styled from "styled-components";

/**
 * Container for all components output related. Receives the isLoading prop from the parent to determine whether to show a loading symbol or the table
 */

const OutlinePane = styled.div`
	//color: #bfc4cf;
`;


export default class NumberLookupOutput extends React.Component
{

	render()
	{

		let display;
		if(this.props.isLoading === true) {
			display = <div style = {{display:'flex', justifyContent:'center', alignItems:'center', paddingTop:'50px'}}> <Loader size="30px"/> </div>
		}
		else if(this.props.output === "500")
		{
			display = <h1>500 Internal Server Error. Service is unavailable.</h1>
		}
		else if(this.props.output != "" ) {
			display = <OutlinePane><ResultTable results = {this.props.output} timestamp = {this.props.timestamp}/></OutlinePane>;
		}


		return (

				<div>
					{
						display
					}
				</div>
		);
	}
}


