import React, { Component } from 'react';
import ResultTable from "./ResultTable";
import {Loader} from '@bandwidth/shared-components';

/**
 * Container for all components output related. Receives the isLoading prop from the parent to determine whether to show a loading symbol or the table
 */

export default class NumberLookupOutput extends React.Component
{

	render()
	{

		let display;
		if(this.props.isLoading === true) {
			display = <div style = {{display:'flex', justifyContent:'center', alignItems:'center', paddingTop:'50px'}}> <Loader size="30px"/> </div>
		}
		else if(this.props.output != "" ) {
			display = <ResultTable results = {this.props.output} timestamp = {this.props.timestamp}/>;
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


