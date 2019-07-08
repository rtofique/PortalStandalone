import React, { Component } from 'react';
import ResultTable from "./ResultTable";
import { validity } from './NumberLookupEnums';

export default class NumberLookupOutput extends React.Component
{

	getJSONValidity()
	{
		if(this.props.output === "") return validity.empty;
		return this.props.output[0].jsonType;
	}

	render()
	{
		const payloadValidity = this.getJSONValidity();

		let resultBody = (payloadValidity != validity.empty) ?
				<ResultTable validity = {payloadValidity} results = {this.props.output}/> :
				"";


		return (
				<div>
					{resultBody}
				</div>
		);
	}
}


