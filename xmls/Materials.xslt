<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
	<html>
	<table border="1">
		<th>Material</th>
		<xsl:for-each select="MaterialList/materials">
		<xsl:sort select="@name"/>
			<tr>
				<td><xsl:value-of select="@id"/></td>
				<td><xsl:value-of select="@name"/></td>
				<td><xsl:value-of select="price"/></td>
				<td><xsl:value-of select="amount"/></td>
			</tr>
		</xsl:for-each>
	</table>
	</html>
</xsl:template>
</xsl:stylesheet>