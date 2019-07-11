import React, { Component } from 'react';
import ResultTable from "./ResultTable";
import { validity } from './NumberLookupEnums';

export default class NumberLookupOutput extends React.Component
{

	/**
	 * Controller that simply determines if need to show output or not
	 * @returns {string|*}
	 */
	isEmpty()
	{
		if(this.props.output === "") return validity.empty;
		return this.props.output[0].jsonType;
	}

	render()
	{
		const isOutputEmpty = this.isEmpty();

		let resultBody = (isOutputEmpty != validity.empty) ?
				<ResultTable results = {this.props.output} timestamp = {this.props.timestamp}/> :
				"";


		return (
				<div>
					{resultBody}
				</div>
		);
	}
}


