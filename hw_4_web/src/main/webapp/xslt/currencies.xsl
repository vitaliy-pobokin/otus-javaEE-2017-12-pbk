<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/*">
        <table border="1">
            <tr>
                <th>Name</th>
                <th>Currency</th>
                <th>Value</th>
            </tr>
            <xsl:for-each select="Valute">
                <xsl:if test="CharCode = 'USD' or CharCode = 'EUR' or CharCode = 'GBP'">
                    <tr>
                        <td><xsl:value-of select="Name"/></td>
                        <td><xsl:value-of select="CharCode"/></td>
                        <td><xsl:value-of select="Value"/></td>
                    </tr>
                </xsl:if>
            </xsl:for-each>
        </table>
    </xsl:template>

</xsl:stylesheet>