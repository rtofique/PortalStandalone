import React, { Component } from 'react';
import ResultTable from "./ResultTable";
import {Loader} from '@bandwidth/shared-components';


export default class NumberLookupOutput extends React.Component
{

	//when fetch is sent, use callback and paint loader instead f of result table
	//when fetch is reevied use callback to paint result table instead

	render()
	{

		return (
				<div>
					{this.props.output != "" &&
						<ResultTable results = {this.props.output} timestamp = {this.props.timestamp}/>
					}
				</div>
		);
	}
}


