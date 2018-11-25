package com.gilvam.cursomc.enums;

public enum TypeClient {

	PERSONINDIVIDUAL(1, "pessoa física"),
	PERSONCORPORATION(2, "pessoa jurídica");

	private int cod;
	private String description;

	TypeClient(int cod, String description) {
		this.cod = cod;
		this.description = description;
	}

	public int getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}

	public static TypeClient toEnum(Integer cod){
		if(cod == null){
			return null;
		}

		for(TypeClient tc : TypeClient.values()){
			if(cod.equals(tc.getCod())){
				return tc;
			}
		}

		throw new IllegalArgumentException("Invalid id: " + cod);
	}
}
